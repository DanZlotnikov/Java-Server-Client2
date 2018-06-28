import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class dbActions {
	
	// Connects to a local db 
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Israel";
			String username = "root";
			String password = "123456";
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			//Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected database successfully!");
			
			return conn;
		} catch (Exception e) {System.out.println(e);}
		
		return null;
	}
	
	@SuppressWarnings("finally")
	public static String dbInsert(HashMap<String, String> data)
	{
		String success = "";
		String hebrewName = data.get("usernameHebrew");
		String englishName = data.get("usernameEnglish");
		String city = data.get("city");
		String street = data.get("street");
		int stNumber;
		try {
			 stNumber = Integer.parseInt(data.get("stNumber"));
		}
		catch(Exception e) {stNumber = 0;}
		
		String phone = data.get("phone");
		int activeCode = Integer.parseInt(data.get("activeCode"));
		
		if (hebrewName == "")
			hebrewName = "-";
		
		if (englishName == "")
			englishName = "-";
		
		if (city== "")
			city = "-";
		
		if (street == "")
			street = "-";
		
		if (phone == "")
			phone = "-";
		
		
		Date dNow = new Date();
	      SimpleDateFormat ft = 
	      new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	    
	    String modificationDate = ft.format(dNow);
	    
	    Connection conn = null;
		Statement stmt = null;
		   try{
		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = getConnection();
		      
		      //STEP 4: Execute a query
		      System.out.println("Inserting records into the table...");
		      stmt = conn.createStatement();
		      
		      String sql = "insert into customers(hebrewName, englishName, city, street, stNumber, phone, activeCode, modificationDate)\r\n" + 
		      		String.format("values ('%1$s', '%2$s', '%3$s', '%4$s', %5$d, '%6$s', %7$d, '%8$s');", hebrewName, englishName, city, street, stNumber, phone, activeCode, modificationDate);
		      stmt.executeUpdate(sql);

		      success = "User Added successfully!";
		      
		   }catch(SQLException se){
			   success  = "Action failed";
		      se.printStackTrace();
		   }catch(Exception e){
			  success  = "Action failed";
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		         {
		            conn.close();
		         }
		         }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		         {
		        	 conn.close();
		         }
		      }
		         catch(SQLException se){
		         se.printStackTrace();
		         success  = "Action failed";
		         }	  
		      return success;
		   }
	}

	@SuppressWarnings("finally")
	public static String dbUpdate(String userCode, HashMap<String, String> data)
	{
		int id;
		try {
			id = Integer.parseInt(userCode);
		}
		catch (Exception e){return "Error: Customer ID must be a number";}
		String success = "";
		String hebrewName = data.get("usernameHebrew");
		String englishName = data.get("usernameEnglish");
		String city = data.get("city");
		String street = data.get("street");
		int stNumber;
		try {
			 stNumber = Integer.parseInt(data.get("stNumber"));
			}
		catch(Exception e) {stNumber = Integer.MIN_VALUE;}
		
		String phone = data.get("phone");
		int activeCode = Integer.parseInt(data.get("activeCode"));
		
		Date dNow = new Date();
	      SimpleDateFormat ft = 
	      new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	    
	    String modificationDate = ft.format(dNow);
	    
	    Connection conn = null;
		Statement stmt = null;
		
		   try{
		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = getConnection();
		      
		      //STEP 4: Execute a query
		      System.out.println("Updating records...");
		      stmt = conn.createStatement();
		      
		      String checkSql = "select * from customers where id = " + id;
			   ResultSet customers = stmt.executeQuery(checkSql);
			   if(!customers.first())
			   	  return "Customer ID does not exist";
		      
			  String sql = "update customers set ";
		      if (!hebrewName.equals(""))
		      {
		    	  sql += "hebrewName = " + "'" + hebrewName + "', ";
		      }
		      
		      if (!englishName.equals(""))
		      {
		    	  sql += "englishName = " + "'" + englishName + "', ";
		      }
		      
		      if (!city.equals(""))
		      {
		    	  sql += "city = " + "'" + city + "', ";
		      }
		      
		      if (!street.equals(""))
		      {
		    	  sql += "street = " + "'" + street + "', ";
		      }
		      
		      if (stNumber != Integer.MIN_VALUE)
		      {
		    	  sql += "stNumber = " + stNumber + ", ";
		      }
		      
		      if (!phone.equals(""))
		      {
		    	  sql += "phone = " + "'" + phone + "', ";
		      }
		      
		      sql += "activeCode = " + "'" + activeCode + "', ";
		      
		      sql += "modificationDate = " + "'" + modificationDate + "' ";
		      
		      sql += "where id = " + id;
		      
		      stmt.executeUpdate(sql);
		      success = "Action success";

		   }catch(SQLException se){
			   success  = "Action failed";
		      se.printStackTrace();
		   }catch(Exception e){
			  success  = "Action failed";
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		         {
		        	success = "Action success";
		            conn.close();
		         }
		         }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		         {
		        	 conn.close();
		         }
		      }
		         catch(SQLException se){
		         se.printStackTrace();
		         success  = "Action failed";
		         }	  
		      return success;
		   }
		
	}
	
	@SuppressWarnings("finally")
	public static String dbDelete(String userCode)
	{
		String success = "";
		int id;
		try {
			id = Integer.parseInt(userCode);
		}
		catch (Exception e){return "Error: Customer ID must be a number";}
		
		Connection conn = null;
		Statement stmt = null;
		   try{

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = getConnection();
		      
		      //STEP 4: Execute a query
		      System.out.println("Updating records...");
		      stmt = conn.createStatement();
		      String sql = "delete from customers where id = "  + id;
		      
		      stmt.executeUpdate(sql);
		      success = "Action success";

		   }catch(SQLException se){
			   success  = "Action failed";
		      se.printStackTrace();
		   }catch(Exception e){
			  success  = "Action failed";
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		         {
		            conn.close();
		         }
		         }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		         {
		        	 conn.close();
		         }
		      }
		         catch(SQLException se){
		         se.printStackTrace();
		         success  = "Action failed";
		         }	  
		      return success;
		   }
		
	}
	
	public static ArrayList<String[]> dbSelect()
	{	
		ResultSet result;
		Connection conn = null;
		Statement stmt = null;
		   try{

			  //STEP 3: Open a connection
			  System.out.println("Connecting to a selected database...");
			  conn = getConnection();
			  
			  //STEP 4: Execute a query
			  System.out.println("Fetching records...");
			  stmt = conn.createStatement();
			  String sql = "select * from customers";
			  
			  result = stmt.executeQuery(sql);
			  System.out.println("Fetched!");
			  
				ArrayList<String[]> usersList = new ArrayList<>(); 
				
				while (result.next())
				{
					String[] userData = new String[9];
					userData[0] = (result.getString("id").toString());
					userData[1] = (result.getString("hebrewName").toString());
					userData[2] = (result.getString("englishName").toString());
					userData[3] = (result.getString("city").toString());
					userData[4] = (result.getString("street").toString());
					userData[5] = (result.getString("stNumber").toString());
					userData[6] = (result.getString("phone").toString());
					userData[7] = (result.getString("activeCode").toString());
					userData[8] = (result.getString("modificationDate").toString());
					usersList.add(userData);
				
				}
				return usersList;

		   }catch(SQLException se){
		      se.printStackTrace();
			   return null;
		   }catch(Exception e){
		      e.printStackTrace();
			   return null;
		   }finally{
		      try{
		         if(stmt!=null)
		         {
		            conn.close();
		         }
		         }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		         {
		        	 conn.close();
		         }
		      }
		         catch(SQLException se){
		         se.printStackTrace();
		         }	  
		   }
	}

	public static String[] dbSearchUser(String userCode)
	{
		String[] userData = new String[9];
		ResultSet result;
		int id;
		try {
			id = Integer.parseInt(userCode);
		}
		catch (Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		try{

			  //STEP 3: Open a connection
			  System.out.println("Connecting to a selected database...");
			  conn = getConnection();
			  
			  //STEP 4: Execute a query
			  System.out.println("Fetching records...");
			  stmt = conn.createStatement();
			  String sql = "select * from customers where id = " + userCode;
			  
			  result = stmt.executeQuery(sql);
			  System.out.println("Fetched!");

				userData[0] = (result.getString("id").toString());
				userData[1] = (result.getString("hebrewName").toString());
				userData[2] = (result.getString("englishName").toString());
				userData[3] = (result.getString("city").toString());
				userData[4] = (result.getString("street").toString());
				userData[5] = (result.getString("stNumber").toString());
				userData[6] = (result.getString("phone").toString());
				userData[7] = (result.getString("activeCode").toString());
				userData[8] = (result.getString("modificationDate").toString());
			
				

		   }catch(SQLException se){
		      se.printStackTrace();
			   return null;
		   }catch(Exception e){
		      e.printStackTrace();
			   return null;
		   }finally{
		      try{
		         if(stmt!=null)
		         {
		            conn.close();
		         }
		         }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		         {
		        	 conn.close();
		         }
		      }
		         catch(SQLException se){
		         se.printStackTrace();
		         }
		      return userData;
		   }
	}
}
