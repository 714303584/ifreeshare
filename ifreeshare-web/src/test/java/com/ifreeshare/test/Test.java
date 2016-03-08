package com.ifreeshare.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	
	public static void main(String[] args) throws JSONException {
		
		JSONArray ja = new JSONArray();
		ja.put("法律");
		ja.put("法律");
		ja.put("法律");
		ja.put("法律");
		JSONObject json = new JSONObject();
		
		json.put("法律文档", ja);
		
		System.out.println(json.toString());
	}

}
