import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
		            success  = "User Added successfully!";
		         }
		         }catch(SQLException se){
		    	  success = "User Added successfully!";
		      }
		      try{
		         if(conn!=null)
		         {
		        	 success  = "User Added successfully!";
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
		      success = "User Updated successfully!";

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
		            success  = "User Updated successfully!";
		         }
		         }catch(SQLException se){
		    	  success = "User Updated successfully!";
		      }
		      try{
		         if(conn!=null)
		         {
		        	 success  = "User Updated successfully!";
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
		      success = "User deleted successfully!";

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
		            success  = "User deleted successfully!";
		         }
		         }catch(SQLException se){
		    	  success = "User deleted successfully!";
		      }
		      try{
		         if(conn!=null)
		         {
		        	 success  = "User deleted successfully!";
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
	
}
