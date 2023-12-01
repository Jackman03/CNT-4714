/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the data user to enter suppliers
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mysql.cj.jdbc.MysqlDataSource;



@SuppressWarnings("serial")
public class supplierEntryServlet extends HttpServlet{
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get parameters from post request
		String snum = request.getParameter("snum");
		String sname = request.getParameter("sname");
		String status1 = request.getParameter("status");
		String city = request.getParameter("city");
		
		try {
			
			if(status1.length() != 0) {	//base case check for no input
		
		//cast string into int
			int status2 = Integer.parseInt(status1);
			
			String rowsReturnedData = "";
			
			int rs = 0;
			
			String query = "insert into suppliers values (?,?,?,?);";
			
			//form sql query here to enter the data
			
			//estblish connection to database
			getDBConnection();
			String reccord = "";
			
			
				statement = connection.prepareStatement(query);
				
				statement.setString(1, snum);
				statement.setString(2, sname);
				statement.setInt(3, status2);
				statement.setString(4, city);
				
				rs = statement.executeUpdate();	
				reccord = "(" + snum + "," + sname +"," + status2 + "," + city +")";
				
				//rowsReturnedData += "update sucessful. " + rs + " rows inserted";			
				rowsReturnedData = "New supplier reccord: " + reccord + " Sucessfully entered into the database" + "\n";
				
				//method to return the rows if the data entry was successful
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedData", rowsReturnedData);
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataEntryHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);  
			}
			else {
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
