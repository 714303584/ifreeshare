package com.ifreeshare.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.ifreeshare.entity.Document;
import com.ifreeshare.tools.PdfToSwfFactory;
import com.ifreeshare.util.ConfigUtil;
import com.ifreeshare.util.FileAccess;
import com.ifreeshare.util.MD5Util;

public class DistributionFactory extends AbstractFactory {
	
	private static DistributionFactory instant = null;
	
	private BlockingQueue<Document> queue = new LinkedBlockingQueue<Document>();
	
	List<String> imgTypes = new ArrayList<String>();
	
	List<String> docTypes = new ArrayList<String>();

	private DistributionFactory(int corePoolSize, int maximumPoolSize, int keepAliveTime, TimeUnit unit, int queueSize) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queueSize);
		this.init();
	}
	
	public static DistributionFactory instant(){
		if(instant == null){
			instant = new DistributionFactory(3, 10, 2,
					TimeUnit.DAYS, 100);
		}
		return instant;
}
	
	private void  init(){
		imgTypes.add("jpg");
		imgTypes.add("png");
		imgTypes.add("jpeg");
		imgTypes.add("bmp");
		imgTypes.add("gif");
		imgTypes.add("psd");
		
		docTypes.add("doc");
		docTypes.add("pdf");
		docTypes.add("txt");
		docTypes.add("docx");
		
		this.workerPool.execute(new DistributionWorker());
	}

	
	public void add(Document doc){
		queue.add(doc);
	}

	
	
	
	
	
	
	
	
	public  class DistributionWorker implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Document document =  queue.take();
					
			
					
					String fileName = document.getFileName();
					String fileType =  FileAccess.getType(fileName).toLowerCase();
					document.setDocTypeName(fileType);
					String fileLocalPath = ConfigUtil.getConfig(ConfigUtil.COVERT_SAVE_PATH)+fileName;
					
					//Calculation file MD5
					String fileMd5 = MD5Util.getFileMD5(fileLocalPath);
					document.setMd5(fileMd5);
							
					if(imgTypes.contains(fileType)){
						
					}else if(docTypes.contains(fileType)) {
						PdfToSwfFactory.instant().add(document);
					}else{
						
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
}