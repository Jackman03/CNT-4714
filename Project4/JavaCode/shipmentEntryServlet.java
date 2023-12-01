/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the data user to enter shipments
*/

import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mysql.cj.jdbc.MysqlDataSource;
public class shipmentEntryServlet extends HttpServlet{
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	private Statement statement2;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String snum = request.getParameter("snum");
		String pnum = request.getParameter("pnum");
		String jnum = request.getParameter("jnum");
		String quantity = request.getParameter("quantity");
		
		try {
		if(quantity.length() != 0) {	//base case to make sure the user has entered data
			
		
		
		int quantity2 = Integer.parseInt(quantity);
		
		
		
		
		String query = "insert into shipments values (?,?,?,?);";
		
		int rs = 0;
		int busniessLogic = 0;
		
		String rowsReturnedData = "";
		
		String reccord = "";
		
		
		getDBConnection();
		
		int rowsUpdated = 0;
		
			statement = connection.prepareStatement(query);
			statement2 = connection.createStatement();	//statement for executing any other commanfs
			
			statement.setString(1, snum);
			statement.setString(2, pnum);
			statement.setString(3, jnum);
			statement.setInt(4, quantity2);
			
			reccord = "(" + snum + "," + pnum +"," + jnum + "," + quantity2 +")";
			
			
			statement2.executeUpdate("drop table if exists beforeShipments;");
			
			//create the new table
			statement2.executeUpdate("create table beforeShipments like shipments;");
			
			//populate the new table
			statement2.executeUpdate("insert into beforeShipments select * from shipments;");
			
			//loop throguh result set and add them to hashmap
			
			//execure the query
			//statement.executeUpdate(rootQuery);
			rowsUpdated = statement.executeUpdate();	
			
			//rowsUpdated = statement.getUpdateCount();
			rowsReturnedData = "New shipment reccord: "+  reccord + " Sucessfully entered into the database" + "\n";
			
			//now we need to update the tables that 
			
			
			statement2.executeUpdate("update suppliers\r\n"
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
			
			busniessLogic = statement2.getUpdateCount();	//if any rows were updated here
			
				//if business logic was triggered
			rowsReturnedData += "Business logic detected! - Updating supplier status. " + " \n";
			rowsReturnedData += "Business logic updated " + busniessLogic + " supplier marks";
	
			statement2.executeUpdate("drop table beforeShipments;");
		
			
			
			
			
			//rowsReturnedData += "update sucessful. " + rs + " rows inserted";			
			
			
			//method to return the rows if the data entry was successful
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedData", rowsReturnedData);
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataEntryHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response); 
			
			
		}
		
		else {	//if the user does not enter data
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedData", "Please enter Data!");
			session.setAttribute("clientQuery", "");
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataEntryHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response);
		}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String errorMessage = "SQL error "+e.getMessage();
			
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedData", errorMessage);
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataEntryHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response); 
		}
		
		
	}
	
	@SuppressWarnings("unused")
	private void getDBConnection() {
		
		try {
		//use a properties file
			String filePath = "C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project-4/WEB-INF/lib/dataentry.properties/";
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
	    	
	    	
	    }
	    
	    catch(SQLException e) {
	    	e.printStackTrace();
	    }
	    catch(IOException e1) {
	    	e1.printStackTrace();
	    }
	}

}
