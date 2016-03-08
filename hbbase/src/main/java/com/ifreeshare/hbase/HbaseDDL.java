package com.ifreeshare.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import com.ifreeshare.entity.Document;

/**
 * hbaseDDL 
 * @author zhuss
 *
 */
public class HbaseDDL {
	
	private  Admin admin;
	
	public HbaseDDL(Admin admin){
		this.admin = admin;
	}
	
	/**
	 * create a hbase table
	 * @param tableName
	 * @throws IOException 
	 */
	public  void  createTable(String tableName,List<String> fimilys) throws IOException{
		if(tableName == null){
				throw new NullPointerException("The name of Table is Null ir Empty!");
		}
		TableName htName =  TableName.valueOf(tableName);
		HTableDescriptor htd = new HTableDescriptor(htName);
		Iterator<String> it = fimilys.iterator();
		while (it.hasNext()) {
			String fim =  it.next();
			HColumnDescriptor hcd = new HColumnDescriptor(fim);
			htd.addFamily(hcd);
		}
		admin.createTable(htd);
	}
	
	/**
	 * Drop Table
	 * @param tableName
	 * @throws IOException 
	 */
	public void dropTable(String tableName) throws IOException{
		if(tableName == null || tableName.length() == 0)
			throw new NullPointerException("The name of Table is Null ir Empty!");
		TableName htName = TableName.valueOf(tableName);
		admin.disableTable(htName);
		admin.deleteTable(htName);
	}
	
	/**
	 * add family to table 
	 * @param tableName
	 * @param familyName
	 * @throws IOException
	 */
	public void addFamily(String tableName ,String familyName) throws IOException{
		admin.addColumn(TableName.valueOf(tableName),new HColumnDescriptor(familyName));
	}
	
	/**
	 * drop family to table
	 * @param tableName
	 * @param familyNam
	 * @throws IOException
	 */
	public void dropFamily(String tableName ,String familyNam) throws IOException{
		admin.deleteColumn(TableName.valueOf(tableName),familyNam.getBytes());
	}
	
	/**
	 * list all TableName
	 * @return
	 * @throws IOException
	 */
	public String[] listTable() throws IOException{
		TableName[] tableNames = admin.listTableNames();
		String[] result = new String[tableNames.length];
		 for (int i = 0; i < tableNames.length; i++) {  
             TableName name = tableNames[i];  
             result[i] = name.getNameAsString();  
             System.out.println("TableName:"+name.getNameAsString());  
         } 
		return result;
	}
	
	/** 
     * The existence of data table 
     * @param tableName 
     * @return 
     * @throws Exception 
     */  
    public boolean tableExists(String tableName) throws Exception {  
        return admin.tableExists(TableName.valueOf(tableName));  
    }  
    
    
    /** 
     * A list of all the tables in the column name 
     * @param tableName  
     * @return 
     * @throws Exception 
     */  
    public String[] getFamilys(String tableName) throws Exception{  
        TableName name = TableName.valueOf(tableName);  
        HTableDescriptor td = admin.getTableDescriptor(name);  
        HColumnDescriptor[] hcds = td.getColumnFamilies();  
        String[] familys = new String[hcds.length];  
        for (int i = 0; i < hcds.length; i++) {  
            HColumnDescriptor hcd = hcds[i];  
            System.out.println("Table Name:"+tableName+ " Family Name:" + hcd.getNameAsString());  
            familys[i] = hcd.getNameAsString();  
        }  
        return familys;  
    }  
    
    
    
	
	public static void main(String[] args) throws IOException {
		
		Configuration configuration =  HBaseConfiguration.create();
    	configuration.set("hbase.zookeeper.quorum", "localhost");
    	
    	Connection connection = ConnectionFactory.createConnection(configuration);
    	
    	Admin admin =  connection.getAdmin();
		
    	HbaseDDL hddl = new HbaseDDL(admin);
    	
    	List<String> fimalys = new ArrayList<String>();
    	
    	String docGrade = Document.class.getSimpleName();;
    	
    	fimalys.add("info");
    	
//    	fimalys.add("display_name");
//    	fimalys.add("parent");
    	
    	

    	hddl.createTable(docGrade, fimalys);
    	
//    	hddl.addFamily(docGrade, "type");
    	
    	
//    	hddl.addFamily(docGrade, "info");
    	
    	
    	
    	
    	
		
	}
	
	
	
	
	

}


