package com.ifreeshare.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ifreeshare.entity.Document;
import com.ifreeshare.util.Pages;

public class DocumentDao  extends  AbstractDao<Document>{

	@Override
	public String getTableName() {
		return Document.class.getSimpleName();
	}

	@Override
	public String getKey() {
		return "key";
	}
	
	
	public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		DocumentDao documentDao = new DocumentDao();
		Document document = new Document();
//		document.setAuthor("zhushunshan");
//		document.setDesc("");
//		document.setDocType(1);
//		document.setDocTypeName("pdf");
//		document.setFileName("小猪.txt");
//		document.setImgPath("/zhushunshan.png");
//		document.setKey("md51231111");
//		document.setLocalPath("/zhuzhu/");
//		document.setMd5("1234561111");
//		document.setPages(100);
//		document.setState(0);
//		document.setPassword("123");
//		document.setTags("zhu,zhunshan,123");
//		documentDao.insert(document);
//		
//		 documentDao.get(document);
//		System.out.println(document);
//		
		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("author","zhushunshan");
//		param.put("key","md5");
		Pages<Document>  pages = new Pages<Document>();
		pages.setPageSize(100);
		
		 documentDao.scan(pages, param);
		 
		 System.out.println(pages.getList().size());
//		 
//		 System.out.println(pages.getList().size());
//		
//		Document doc = new Document();
//		
//		Map<String, String > map =  BeanUtil.getClassFiledType(doc.getClass());
//		
//		
//		
////		Map<String, Object> map =  BeanUtil.transBean2Map(doc);
//		
//		Iterator< String > it = map.keySet().iterator();
//		
//		while (it.hasNext()) {
//			String key = it.next();
//		System.out.println(key+map.get(key));
//		}
//		
	}

	@Override
	public String getBeanKey(Document t) {
		return t.getKey();
	}

	@Override
	public Document getNowT() {
		return new Document();
	}

		

}
