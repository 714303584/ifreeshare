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
import com.ifreeshare.service.DictionaryService;
import com.ifreeshare.service.DocumentService;
import com.ifreeshare.util.CacheUtil;

public class TypeListServlet extends HttpServlet {
	
	DocumentService documentService = new DocumentService();
	
	DictionaryService dictionaryService = new DictionaryService(); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 自动生成的方法存根
		req.setAttribute("types", CacheUtil.map);
		
		req.getRequestDispatcher("/all.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 自动生成的方法存根
		req.getRequestDispatcher("/all.ftl").forward(req, resp);
	}

}
