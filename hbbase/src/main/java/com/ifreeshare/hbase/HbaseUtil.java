package com.ifreeshare.hbase;

import org.apache.hadoop.hbase.util.Bytes;

public class HbaseUtil {
	
	
	
	
	public static byte[] getByte(Object obj){
		byte[] b = null;
		if(obj instanceof String){
			String str = (String)obj;
			b = Bytes.toBytes(str);
		}else if(obj instanceof Integer){
			int i = Integer.parseInt(obj.toString());
			b = Bytes.toBytes(i);
		}else if(obj == null){
			return b;
		}
		
		return b;
	}
	
	
	public static void main(String[] args) {
		
		int b = 0;
		
		Object ob = b;
		
		System.out.println(ob.getClass());
		
	}

}
