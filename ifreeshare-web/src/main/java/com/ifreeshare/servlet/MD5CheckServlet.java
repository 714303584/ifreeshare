package com.ifreeshare.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.yarn.webapp.view.HtmlPage.Page;
import org.json.JSONException;
import org.json.JSONObject;

import com.ifreeshare.entity.Document;
import com.ifreeshare.service.DocumentService;
import com.ifreeshare.util.IoUtil;
import com.ifreeshare.util.Pages;

public class MD5CheckServlet extends HttpServlet {
	
	DocumentService documentService = new DocumentService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		try {
			String md5 = req.getParameter("fileMd5");
			Document doc = documentService.get(md5);
			
			Map<String, Object> filter = new HashMap<String,Object>();
			
			
			Pages<Document>  page = documentService.list(null, filter, 100);
			if(page.getList().size() > 0){
				json.put("result", "exist");
			}else{
				json.put("result", "notFound");
			}
			resp.getWriter().write(json.toString());
			IoUtil.close(resp.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
}
