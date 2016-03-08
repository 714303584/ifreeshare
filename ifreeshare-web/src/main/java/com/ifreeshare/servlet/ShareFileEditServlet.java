package com.ifreeshare.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ifreeshare.util.IoUtil;

public class ShareFileEditServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getParameter("fileName");
		String fileId = req.getParameter("id");
		
		
		JSONObject json = new JSONObject();
		try {
			json.put("result", "ok");
			resp.getWriter().write(json.toString());
			IoUtil.close(resp.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		super.doGet(req, resp);
	}
	
	
	
	
	

}
