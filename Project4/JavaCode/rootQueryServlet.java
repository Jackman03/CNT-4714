/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the root user to run queries
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class rootQueryServlet extends HttpServlet{
	
	private Connection connection = null;
	private Statement statement = null;
	private int updateValue;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		//try {
		getDBConnection();
		//extract the client query
		String rootQuery = request.getParameter("rootQuery");

			
		
		int busniessLogic;
		
		String  rowsReturnedRoot = "";
		updateValue = 0;
	
		
		
		//if the user does not enter a string nothing happens
		//trim the string to protect aginst any sql injections or whatnot.
		rootQuery = rootQuery.trim();	
		
		//we need to decide if this is a select or other statement
	
		ResultSet rs = null;
		int rowsUpdated = 0;
		
		Boolean entered = true;
		
		try {
			//select statment
			
			
			if(rootQuery.length() != 0) {

			if(rootQuery.substring(0, 1).equalsIgnoreCase("s")) {
				rs = statement.executeQuery(rootQuery);
				ResultSetToHTML returnTable = new ResultSetToHTML();
				
				rowsReturnedRoot = returnTable.getHTMLTable(rs);
				
				
				
				//get the session from the jsp page
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedRoot", rowsReturnedRoot);
				session.setAttribute("rootQuery", rootQuery);
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);  
			}
			
			else {
				//update stetatment
				
				
				
				if(rootQuery.contains("shipments")) {	//check if business logic needs to be applied
					
					statement.executeUpdate("drop table if exists beforeShipments;");
					
					//create the new table
					statement.executeUpdate("create table beforeShipments like shipments;");
					
					//populate the new table
					statement.executeUpdate("insert into beforeShipments select * from shipments;");
					
					//loop throguh result set and add them to hashmap
					
					//execure the query
					statement.executeUpdate(rootQuery);
					
					rowsUpdated = statement.getUpdateCount();
					rowsReturnedRoot += "The statement updated sucessfuly" + "\n" + rowsUpdated + "row updated" + "\n"; 
					
					//now we need to update the tables that 
					
					
					statement.executeUpdate("update suppliers\r\n"
							+ "set status = status + 5\r\n"
							+ "where suppliers.snum in\r\n"
							+ "(select distinct snum\r\n"
							+ "from shipments\r\n"
							+ "where shipments.quantity >= 100\r\n"
							+ "and\r\n"
							+ "not exists (select *\r\n"
							+ "from beforeShipments\r\n"
							+ "where shipments.snum = beforeShipments.snum\r\n"
							+ "and shipments.pnum = beforeShipments.pnum\r\n"
							+ "and shipments.jnum = beforeShipments.jnum\r\n"
							+ "and beforeShipments.quantity >= 100\r\n"
							+ ")\r\n"
							+ ");");
					
					busniessLogic = statement.getUpdateCount();	//if any rows were updated here
					
						//if business logic was triggered
					rowsReturnedRoot += "Business logic detected! - Updating supplier status. " + " \n";
					rowsReturnedRoot += "Business logic updated " + busniessLogic + " supplier marks";
					
	
					statement.executeUpdate("drop table beforeShipments;");
					
					
				}
				
				
				else {
					rowsUpdated = statement.executeUpdate(rootQuery);
					rowsUpdated = statement.getUpdateCount();
					rowsReturnedRoot += "The statement updated sucessfuly" + "\n" + rowsUpdated + "row updated" + "\n"; 
					rowsReturnedRoot += "Business logic not triggered"; 
				}
				
				

				
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedRoot", rowsReturnedRoot);
				session.setAttribute("rootQuery", rootQuery);
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);  
			}
			
			}
			
			else {
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedRoot", "Please enter a query!");
				session.setAttribute("rootQuery", "");
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);
			}
			
			
			
		}
		catch(Exception e) {
			String errorMessage = "SQL error "+e.getMessage() + "rootQuery length: "+ rootQuery.length();
			
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedRoot", errorMessage);
			session.setAttribute("rootQuery", rootQuery);
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response);  

		}
	}
	
	
	private void getDBConnection() {
		
		try {
		//use a properties file
			String filePath = "C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project-4/WEB-INF/lib/root.properties/";
			Properties properties = new Properties();
		    FileInputStream filein = null;
		    MysqlDataSource dataSource = null;
	    
	    
	    	filein = new FileInputStream(filePath);
	    	properties.load(filein);
	    	dataSource = new MysqlDataSource();
	    	//parse the datasource object from properties file
	    	dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
	    	dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
	    	dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
	    	
	    	//establish the connection
	    	connection = dataSource.getConnection();
	    	statement = connection.createStatement();
	    	
	    }
	    
	    catch(SQLException e) {
	    	e.printStackTrace();
	    }
	    catch(IOException e1) {
	    	e1.printStackTrace();
	    }
	}

}
