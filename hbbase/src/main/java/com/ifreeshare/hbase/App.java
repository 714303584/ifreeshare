package com.ifreeshare.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	
    	Configuration configuration =  HBaseConfiguration.create();
    	configuration.set("hbase.zookeeper.quorum", "localhost");
    	
    	Connection connection = ConnectionFactory.createConnection(configuration);
    	Table table =   connection.getTable(TableName.valueOf("member"));
    	
    	Scan  scan = new Scan();
    	
    	 ResultScanner rs =  table.getScanner(scan);
    	 
    	 Result  result = null;
    	 while((result = rs.next()) != null){
    		 	
//    		 result.
    		 
    	 }
    	
    	
    }
}
