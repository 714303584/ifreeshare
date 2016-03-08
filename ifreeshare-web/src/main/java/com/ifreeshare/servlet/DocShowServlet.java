package com.ifreeshare.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifreeshare.entity.Document;
import com.ifreeshare.service.DocumentService;
import com.ifreeshare.util.Pages;

public class DocShowServlet extends HttpServlet {
	DocumentService documentService = new DocumentService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String md5 =  req.getParameter("md5");
		if(!(md5 == null) && !"".equals(md5)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("md5",md5);
			Pages<Document> docs = documentService.list(null, map, 10);
			if(docs.getList() != null &&  docs.getList().size() > 0){
				req.setAttribute("docInfo", docs.getList().get(0));
				req.getRequestDispatcher("/doc_show.ftl").forward(req, resp);
			}else{
				resp.getOutputStream().print("Not Found!");
			}
		}else{
//			req.getRequestDispatcher("/404.ftl").forward(req, resp);
			resp.getOutputStream().print("Not Found!");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
