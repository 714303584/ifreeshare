package com.ifreeshare.hbase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDQL {

	private static  Configuration conf = null ;

	private final static String UTF8 = "UTF-8";

	static{
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "localhost");
	}
	

	public HbaseDQL() {
		super();
	}

	public HbaseDQL(Connection connection) {
		super();
	}
	
	
	public static Table getTable(String tableName) throws IOException{
		return  conn().getTable(TableName.valueOf(tableName));
	}
	

	public static   void insert(String tableName, String key, String family, String qualifier, String value) throws IOException {
		Table htable =conn() .getTable(TableName.valueOf(tableName));
		Put put = new Put(Bytes.toBytes(key));
		put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),Bytes.toBytes(value));
		htable.put(put);
	}
	

	public static void insert(String tableName, byte[] key, String family, byte[] qualifier, byte[] value) throws IOException {
		Table htable =conn() .getTable(TableName.valueOf(tableName));
		Put put = new Put(key);
		put.add(Bytes.toBytes(family),qualifier,value);
		htable.put(put);
		
	}
	
	public static Connection conn() throws IOException{
		return ConnectionFactory.createConnection(conf);
	}

	public static Result getRow(String tableName, String key) throws IOException {
		Table table = conn().getTable(TableName.valueOf(tableName));
		Get gt = new Get(Bytes.toBytes(key));
		Result res = table.get(gt);
		return res;
	}

	public static Result getRow(String tableName, String key, String family) throws IOException {
		Table table = conn().getTable(TableName.valueOf(tableName));
		Get gt = new Get(Bytes.toBytes(key));
		Result res = table.get(gt);
		return res;
	}
	
	public static void delRow(String tableName , String key) throws IOException{
		Table table = conn().getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(Bytes.toBytes(key));
		table.delete(delete);
	}
	
	
	
	
	public static String getValue(Result rs, String family, String qualifier) throws UnsupportedEncodingException {
		String result = null;
		Cell cell = rs.getColumnLatestCell (Bytes.toBytes(family), Bytes.toBytes(qualifier));
		result = new String(cell.getValue(), UTF8);
		return result;
	}
	
	
	public static ResultScanner  scan(String tableName, String family,Map<String,String> map,String startRow) throws IOException{
		Table table = conn().getTable(TableName.valueOf(tableName));
		ResultScanner rs = null;
		Scan scan = new Scan();
		
		scan.addFamily(Bytes.toBytes(family));
		if(startRow != null){
			scan.setStartRow(Bytes.toBytes(startRow));
		}
		
		FilterList filterList = new FilterList();
		Iterator<String> it =  map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value =  map.get(key);
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),Bytes.toBytes(key), CompareOp.EQUAL, Bytes.toBytes(value));
			filterList.addFilter(filter);
		}
		scan.setFilter(filterList);
		
		rs =  table.getScanner(scan);
		return rs;
	}
	

	
	public static String getKey(Result result) throws UnsupportedEncodingException{
			return new String(result.getRow(),UTF8);
	}
	
	
	
	// public void
	public static void main(String[] args) throws IOException {

		

		HbaseDQL hdql = new HbaseDQL(ConnectionFactory.createConnection(conf));
		//		String doc_Grade = "doc_grade";
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("parentKey", "医疗");
//		map.put("type", "1");
		
		
		ResultScanner rs =  HbaseDQL.scan("Dictionary","info",  map, null);
		
		  for (Result r : rs) {
	            for (KeyValue kv : r.raw()) {
	            	System.out.println(new String(r.getRow(),UTF8));
	                System.out.println(String.format("row:%s, family:%s, qualifier:%s, qualifiervalue:%s, timestamp:%s.", 
	                        Bytes.toString(kv.getRow()), 
	                        Bytes.toString(kv.getFamily()), 
	                        Bytes.toString(kv.getQualifier()), 
	                        Bytes.toString(kv.getValue()),
	                        kv.getTimestamp()));
	            }
	        }
	        
		

		// hdql.insert(doc_Grade, "doc", "info", "displayName","doc");

//		Result rs = hdql.getRow(doc_Grade, "结构化查询语言");
//
//		byte[] bt = rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("displayName"));
//
//		System.out.println(Bytes.toString(bt));
		
		
		
		
		
		
		
		
		
		
		

		// rs.getv

		// for (KeyValue entry : rs.raw()) {
		// System.out.println("*******************************************************************");
		// System.out.println("Family:"+new String(entry.getFamily(), "UTF-8"));
		// System.out.println("Row:"+new String(entry.getRow(),"UTF-8"));
		// System.out.println("Qualifier:"+new String(entry.getQualifier(),
		// "UTF-8"));
		// System.out.println("Timestamp:"+entry.getTimestamp());
		// System.out.println("Value:"+new String(entry.getValue(),"UTF-8"));
		// System.out.println("*******************************************************************");
		// }

	}


}
