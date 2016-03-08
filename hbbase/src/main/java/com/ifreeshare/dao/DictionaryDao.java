package com.ifreeshare.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import com.ifreeshare.entity.Dictionary;
import com.ifreeshare.hbase.HbaseDQL;

public class DictionaryDao {
	
	public String  getTableName(){
		return "Dictionary";
	}
	
	
	public void insert(Dictionary dictionary) throws IOException{
		HbaseDQL.insert(getTableName(), dictionary.getKey(), FinalUtil.INSERT_FAMILY, "displayName", dictionary.getDisplayName());
		HbaseDQL.insert(getTableName(), dictionary.getKey(), FinalUtil.INSERT_FAMILY, "type", String.valueOf(dictionary.getType()));
		HbaseDQL.insert(getTableName(), dictionary.getKey(), FinalUtil.INSERT_FAMILY, "parentKey", dictionary.getParentKey());
		HbaseDQL.insert(getTableName(), dictionary.getKey(), FinalUtil.INSERT_FAMILY, "desc", dictionary.getDisplayName());
	}
	
	
	public Dictionary get(String key) throws IOException{
		Dictionary d = null;
		Result rs = HbaseDQL.getRow(getTableName(), key);
		if(!rs.isEmpty()){
			d = new Dictionary();
			d.setKey(key);
			d.setDisplayName(HbaseDQL.getValue(rs, FinalUtil.INSERT_FAMILY, "displayName"));
			d.setType(Integer.parseInt(HbaseDQL.getValue(rs, FinalUtil.INSERT_FAMILY, "type")));
			d.setParentKey(HbaseDQL.getValue(rs, FinalUtil.INSERT_FAMILY, "parentKey"));
			d.setDesc(HbaseDQL.getValue(rs, FinalUtil.INSERT_FAMILY, "desc"));
		}
		return d;
	}
	
	public void delete(String key) throws IOException{
		HbaseDQL.delRow(getTableName(), key);
	}
	
	public List<Dictionary> page(String startRow,Map<String,String> filters,int size) throws IOException{
		List<Dictionary> result = new ArrayList<Dictionary>();
		ResultScanner rs = HbaseDQL.scan(getTableName(),FinalUtil.INSERT_FAMILY,filters, startRow);
		 Result[] results = rs.next(size);
		 for (int i = 0; i < results.length; i++) {
			Result res = results[i];
			Dictionary d = new Dictionary();
			d.setKey(HbaseDQL.getKey(res));
			d.setDisplayName(HbaseDQL.getValue(res, FinalUtil.INSERT_FAMILY, "displayName"));
			d.setType(Integer.parseInt(HbaseDQL.getValue(res, FinalUtil.INSERT_FAMILY, "type")));
			d.setParentKey(HbaseDQL.getValue(res, FinalUtil.INSERT_FAMILY, "parentKey"));
			d.setDesc(HbaseDQL.getValue(res, FinalUtil.INSERT_FAMILY, "desc"));
			result.add(d);
		}
		return result;
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		DictionaryDao dd = new DictionaryDao();
		
		String [] str = {"稽查与征管/审计","网络营销/经济"};
//				,"琴棋书画","两性感情"
//				,"游戏攻略","武术","期刊杂志","保健养生","宠物","摄影摄像"};
		
		for (int i = 0; i < str.length; i++) {
			Dictionary d = new Dictionary();
			d.setKey(str[i]);
			d.setDisplayName(str[i]);
			d.setDesc("分类子级!");
			d.setParentKey("经济/贸易/财会");
			d.setType(FinalUtil.DOC_CLASSIFICATION);
//			
			
			dd.insert(d);
		}
		
		
		
//		Map<String, String> map = new HashMap<String, String>();
//		
//		map.put("parentKey", "生活休闲");
//		
//		map.put("type", Integer.toString(FinalUtil.DOC_CLASSIFICATION));
//		
//		
//	    List<Dictionary> list =	dd.page(null, map,10);
//	    
//	    System.out.println(list);
//	    System.out.println(list.size());
		
		
		
	}
	
	

}
