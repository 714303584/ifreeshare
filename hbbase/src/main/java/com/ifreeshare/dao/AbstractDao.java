package com.ifreeshare.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.ifreeshare.entity.Dictionary;
import com.ifreeshare.hbase.HbaseDQL;
import com.ifreeshare.util.BeanUtil;
import com.ifreeshare.util.Pages;

public abstract class AbstractDao<T> {
	
	protected Map<String, String> nameAndTypes = null;;
	
	public void insert(T t) throws IOException{
		Map<String , Object> map =  BeanUtil.transBean2Map(t);
		Iterator<String> it = map.keySet().iterator();
		Object key =  map.get(getKey());
		while (it.hasNext()) {
			String qualifier = it.next();
			Object obj = map.get(qualifier);
			if(obj != null){
				HbaseDQL.insert(getTableName(), getBytes(key),FinalUtil.INSERT_FAMILY , getBytes(qualifier),getBytes(obj));
			}
		}
	}
	
	public void delete(String key) throws IOException{
		HbaseDQL.delRow(getTableName(), key);
	}
	
	
	
	public T get(T t) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		  Result result = HbaseDQL.getRow(getTableName(), getBeanKey(t));
		  parseResultToT(t, result);
		  return t;
	}
	
	
	public Pages<T> scan(Pages<T> pages,Map<String ,Object> param) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		 Table table =HbaseDQL.getTable(getTableName());
		 ResultScanner rs = null;
		 Scan scan = new Scan();
		 scan.addFamily(Bytes.toBytes(FinalUtil.INSERT_FAMILY));
		 String startRow = pages.getStartRow();
			if(startRow != null){
				scan.setStartRow(Bytes.toBytes(startRow));
			}
			FilterList filterList = new FilterList();
			Iterator<String> it =  param.keySet().iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				Object obj = param.get(fieldName);
				byte[] bytes =  getBytes(obj);
				BinaryPrefixComparator comparator = new BinaryPrefixComparator(bytes);
				Filter filter = new SingleColumnValueFilter(Bytes.toBytes(FinalUtil.INSERT_FAMILY),Bytes.toBytes(fieldName), CompareOp.EQUAL, comparator);
				//DependentColumnFilter(Bytes.toBytes(FinalUtil.INSERT_FAMILY),Bytes.toBytes(fieldName), false,CompareOp.EQUAL, comparator);
				filterList.addFilter(filter);
			}
			if(param.size() > 0){
				scan.setFilter(filterList);
			}
			
			rs =  table.getScanner(scan);
			 Result[] results = rs.next(pages.getPageSize());
			 List<T> result = new ArrayList<T>();
			 for (int i = 0; i < results.length; i++) {
				 System.out.println(i);
				Result res = results[i];
				T t =  getNowT();
				parseResultToT(t, res);
				result.add(t);
			}
			 pages.setList(result);
		return pages;
	}
	
	public abstract String  getTableName();
	
	public abstract String getKey();
	
	public abstract String getBeanKey(T t);
	
	public abstract T getNowT();
	
	
	
	
	public void parseResultToT(T t,Result result) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Map<String,String> map =  BeanUtil.getClassFiledType(t.getClass());
		Iterator<String> it = map.keySet().iterator();
		Class clazz = t.getClass();
		while (it.hasNext()) {
			String filedName = it.next();
			Cell cell = result.getColumnLatestCell (Bytes.toBytes(FinalUtil.INSERT_FAMILY), Bytes.toBytes(filedName));
			if(cell != null){
				 byte[] bytes = cell.getValue();
				 String type = map.get(filedName);
				 Field field =  clazz.getDeclaredField(filedName);
				 field.setAccessible(true);
				 if("int".equals(type)){
					  int i = Bytes.toInt(bytes);
					field.set(t,i);
				 }else if("String".equals(type)){
					 String i = Bytes.toString(bytes);
						field.set(t,i);
				 }else if("long".equals(type)){
					 Long l = Bytes.toLong(bytes);
					 field.set(t,l);
				 }else if("double".equals(type)){
					 double d = Bytes.toDouble(bytes);
					 field.set(t, d);
				 }else if("boolean".equals(type)){
					 field.set(t, Bytes.toBoolean(bytes));
				 }else if ("float".equals(type)){
					 field.set(t, Bytes.toFloat(bytes));
				 }else if("short".equals(type)){
					 field.set(t,Bytes.toShort(bytes));
				 }
			}
		}
	}
	
	public byte[] getBytes(Object obj){
		
		byte[] bytes = null;
		String objtype = obj.getClass().getSimpleName();
		if(obj instanceof String){
			bytes = Bytes.toBytes(obj.toString());
		}else if(obj instanceof Date){
//			bytes = Bytes.tob
			
		}else if("Long".equals(objtype)){
			long i = (Long)obj;
			bytes = Bytes.toBytes(i);
		}else if("Double".equals(objtype)){
			double d = (Double) obj;
			bytes = Bytes.toBytes(d);
		}else if("Integer".equals(objtype)){
			int i =  (Integer)obj;
			bytes = Bytes.toBytes(i);
		}else if("Boolean".equals(objtype)){
			boolean b = (Boolean) obj;
			bytes = Bytes.toBytes(b);
		}else if("Float".equals(objtype)){
			float c = (Float)obj;
			bytes = Bytes.toBytes(c);
		}else if("Short".equals(objtype)){
			short c = (Short)obj;
			bytes = Bytes.toBytes(c);
		}else if("Character".equals(objtype)){
			char c = (Character)obj;
			bytes = Bytes.toBytes(c);
		}else if("Byte".equals(objtype)){
			byte b = (Byte)obj;
			bytes = Bytes.toBytes(b);
		}else{
		}
		
		return bytes;
	}
	
	

}
