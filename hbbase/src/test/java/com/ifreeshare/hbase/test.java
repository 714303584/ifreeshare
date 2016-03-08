package com.ifreeshare.hbase;

public class test {
	
	
	public static void main(String[] args) {
		
		String i = "";
//		System.out.println(i instanceof long);
		long j = 1l;
		
		Object obj = j;
		
		System.out.println(obj.getClass().getSimpleName() );
		
		double m = 1.0;
		obj = m;
		System.out.println(obj.getClass().getSimpleName() );
		
		int n = 1;
		obj = n;
		

		System.out.println(obj.getClass().getSimpleName() );
		
		
		
		
		
	}

}
