package com.ifreeshare.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifreeshare.util.CacheUtil;

public class ShareServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("types", CacheUtil.map);
		req.setAttribute("docTypesvalue", CacheUtil.json.toString());
		
		Iterator it =CacheUtil.map.values().iterator();
		it.hasNext();
		req.setAttribute("oneChilds", it.next());
		req.getRequestDispatcher("/share.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 自动生成的方法存根
		req.getRequestDispatcher("/share.ftl").forward(req, resp);
	}
	
	

}
