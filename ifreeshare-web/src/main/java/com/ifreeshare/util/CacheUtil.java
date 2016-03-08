package com.ifreeshare.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ifreeshare.dao.FinalUtil;
import com.ifreeshare.entity.Dictionary;
import com.ifreeshare.service.DictionaryService;

public class CacheUtil {
	 static DictionaryService dictionaryService = new DictionaryService(); 
	
	public static  Map<String, List<Dictionary>> map = new HashMap<String,List<Dictionary>>();
	public static JSONObject json = new JSONObject();
	
	static{
		Map<String,String> filter = new HashMap<String, String>();
		filter.put("parentKey", "父");
		filter.put("type",FinalUtil.DOC_CLASSIFICATION+"");
		try {
			List<Dictionary> list =  dictionaryService.list(null, filter, 100);
			for (int i = 0; i < list.size(); i++) {
				Map<String,String> filter2 = new HashMap<String, String>();
				Dictionary father = list.get(i);
				filter2.put("parentKey", father.getKey());
				filter2.put("type",FinalUtil.DOC_CLASSIFICATION+"");
				List<Dictionary> result =  dictionaryService.list(null, filter2,1000);
				JSONArray jsonArray  = new JSONArray();
				for (int j = 0; j < result.size(); j++) {
					jsonArray.put(result.get(j).getKey());
				}
				json.put(father.getKey(), jsonArray);
				map.put(father.getKey(),result);
			}
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
