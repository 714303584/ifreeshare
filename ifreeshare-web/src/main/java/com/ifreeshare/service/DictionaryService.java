package com.ifreeshare.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ifreeshare.dao.DictionaryDao;
import com.ifreeshare.entity.Dictionary;

public class DictionaryService {
	
	DictionaryDao dictionaryDao = new DictionaryDao();
	
	
	
	
	
	
	
	
	public List<Dictionary> list(String startRow,Map<String,String> filters,int size) throws IOException {
		return dictionaryDao.page(startRow, filters, size);
	}
	
	
	
	
	
	
	
//	public List<Dictionary> 
	

}
