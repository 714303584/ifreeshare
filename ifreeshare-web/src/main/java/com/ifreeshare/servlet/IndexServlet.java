package com.ifreeshare.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifreeshare.dao.FinalUtil;
import com.ifreeshare.entity.Dictionary;
import com.ifreeshare.entity.Document;
import com.ifreeshare.service.DictionaryService;
import com.ifreeshare.service.DocumentService;


public class IndexServlet extends HttpServlet {
	DocumentService documentService = new DocumentService();
	
	DictionaryService dictionaryService = new DictionaryService(); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String,String> filter = new HashMap<String, String>();
		Map<String,Object> docFilter = new HashMap<String, Object>();
		filter.put("parentKey", "父");
		filter.put("type",FinalUtil.DOC_CLASSIFICATION+"");
		List<Dictionary> list =  dictionaryService.list(null, filter, 100);
		req.setAttribute("docTypes", list);
		req.setAttribute("hotDownload",documentService.list(null, docFilter, 25).getList());
		req.getRequestDispatcher("/index.ftl").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		
		
		req.getRequestDispatcher("/index.ftl").forward(req, resp);
		
	}
	
	

}
