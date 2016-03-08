package com.ifreeshare.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ifreeshare.dao.DocumentDao;
import com.ifreeshare.entity.Document;
import com.ifreeshare.util.Pages;

public class DocumentService {
	DocumentDao docdao = new DocumentDao();
	
	public  void add(Document doc) {
		try {
			docdao.insert(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Document get(String key){
		Document doc = new Document();
		doc.setKey(key);
		 try {
			doc =  docdao.get(doc);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return doc;
	}
	
	
	public Pages<Document> list(String startRow,Map<String,Object> filter,int size){
////		Document[] docArray = docInfos.toArray();
		Pages<Document> page = new Pages<Document>();
		page.setPageSize(size);
		
		try {
			docdao.scan(page, filter);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (Document document : docInfos) {
//			result.add(document);
//			if(result.size() == size){
//				break;
//			}
//		}
		return page;
	}
	
}
