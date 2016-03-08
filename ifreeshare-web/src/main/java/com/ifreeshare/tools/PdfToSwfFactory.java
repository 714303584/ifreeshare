package com.ifreeshare.tools;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.ifreeshare.entity.Document;
import com.ifreeshare.service.DocumentService;
import com.ifreeshare.util.ConfigUtil;
import com.ifreeshare.util.FileAccess;
import com.ifreeshare.util.ImgUtil;
import com.ifreeshare.util.MD5Util;
import com.ifreeshare.util.PdfUtil;

public class PdfToSwfFactory {
	
	
	public static final String OPEN_OFFICE_HOST = "open.office.host";
	
	public static final String OPEN_OFFICE_PORT = "open.office.port";
	
	public static final String COVERT_SAVE_PATH = "covert.save.path";
	
	public static final String COVERT_TOOL_PATH = "covert.tool.path";
	
	public static final String DEFAUT_THUMBNAIL_SAVE_PATH = "/md5/thumbnail/";
	
	public static final String DEFAUT_SWF_SAVE_PATH = "/md5/SWF/";

	public static final String DOC_TYPE_PDF = "pdf";
	
	private static PdfToSwfFactory pdfToSwfFactory = null;
	
	
	ThreadPoolExecutor pdfToSwfWorkerPool = null;
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private   TimeUnit unit;
	private int queueSize;
	BlockingQueue<Runnable> workQueue = null;
	BlockingQueue<Document>	 docQueue = null;
	Logger log = null;
	
	public PdfToSwfFactory() {
		super();
	}
	
	
	public static PdfToSwfFactory instant(){
		if(pdfToSwfFactory == null){
			pdfToSwfFactory = new PdfToSwfFactory(3, 10, 2,
					TimeUnit.DAYS, 100);
		}
		return pdfToSwfFactory;
	}

	public PdfToSwfFactory(int corePoolSize, int maximumPoolSize, int keepAliveTime,
			TimeUnit unit, int queueSize) {
		super();
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.unit = unit;
		this.queueSize = queueSize;
		init();
	}

	private void init() {
		workQueue = new ArrayBlockingQueue<Runnable>(this.maximumPoolSize);
		docQueue = new LinkedBlockingQueue<Document>();
		pdfToSwfWorkerPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		
		for (int i = 0; i < corePoolSize; i++) {
			pdfToSwfWorkerPool.execute(new Pdf2SwfWorker());
		}
		
		 log = LogManager.getLogger(PdfToSwfFactory.class);
		
	}

	
	public void  add(Document doc) {
//		if(docQueue.size() > queueSize){
//			throw new Exception(" The queue is full");
//		}else{
			docQueue.add(doc);
//		}
	}
	
	public static String getFileType(String file){
		return file.substring(file.lastIndexOf('.')+1, file.length());
	}
	
	public static void startSOffice() throws IOException{
		Runtime  r = Runtime.getRuntime();
		r.exec(ConfigUtil.getConfig(COVERT_TOOL_PATH));
	}
	
static DocumentService documentService = new DocumentService();


	public class Pdf2SwfWorker implements Runnable {
		public void run() {
			Document doc = null;
				while (true) {
					try {
						doc = docQueue.take();
						String password  = doc.getPassword();
						String localPath = doc.getLocalPath();
						String fileMd5 = doc.getMd5();
						String pdfPath = null;
						String fileType = getFileType(localPath);
						if(!DOC_TYPE_PDF.equals(fileType)){
							System.out.println(localPath);
							String sourcePath = localPath;
							pdfPath = ConfigUtil.getConfig(COVERT_SAVE_PATH)+fileMd5+"."+DOC_TYPE_PDF;
							File outputFile = new File(pdfPath);
							if("txt".equals(fileType)){
								String odtPath = ConfigUtil.getConfig(COVERT_SAVE_PATH)+fileMd5+".odt";
								FileAccess.Copy(localPath, odtPath);
								sourcePath = odtPath;
								System.out.println(sourcePath);
							}
							File inputFile = new File(sourcePath);
							OpenOfficeConnection  connection = new SocketOpenOfficeConnection(ConfigUtil.getConfig(OPEN_OFFICE_HOST), 8100);
							connection.connect();
							if(connection.isConnected()){
								DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
								converter.convert(inputFile, outputFile);
								connection.disconnect();
							}else{
								Exception ex = new Exception("soffice");
								throw ex;
							}
						}else{
							pdfPath = localPath;
						}
						
						PDDocument pdfDocument = PDDocument.loadNonSeq(new File(pdfPath), null, "");
						if(pdfDocument != null){
							 if (pdfDocument.isEncrypted())
					          {
								 try {
									pdfDocument.decrypt(password);
								} catch (CryptographyException e) {
									e.printStackTrace();
								}
					          }
							doc.setMd5(fileMd5);
							int pageSize = pdfDocument.getNumberOfPages();
							doc.setPages(pageSize);
							List<PDPage> pages = PdfUtil.getPages(pdfDocument);
							String thumbnail = ConfigUtil.getThumbnailSavePath()+doc.getMd5();
							String thumbnailPath = doc.getMd5()+"1."+ImgUtil.IMG_TYPE_JPG;
							System.out.println(thumbnail);
							PdfUtil.pdfPage2Img(pdfDocument, "", 1, 1, thumbnail, ImgUtil.IMG_TYPE_JPG);
							doc.setImgPath(thumbnailPath);
						}
						pdfDocument.close();
						
						String swf = ConfigUtil.getSwfSavePath()+doc.getMd5()+"%."+"swf";
						PdfUtil.ConvertToSwf(pdfPath, swf, 0);
						log.info("ConvertToSwf"+doc.getMd5());
						System.out.println(Thread.currentThread().getName()+"文件的MD5:"+doc.getMd5());
						System.err.println("doc:"+doc);
						documentService.add(doc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ConnectException e) {
						e.printStackTrace();
//							try {
////								startSOffice();
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//							docQueue.add(doc);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
						
					}
					
				}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
//		Document doc = new Document();
//		doc.setLocalPath("D:\\api.pdf");
//		
//		PdfToSwfFactory.instant().add(doc);
		
		PdfToSwfFactory.startSOffice();
	
		
	}
	
	
	
	
	
	
	
	

}
