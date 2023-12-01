/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the data user to enter parts
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class partEntryServlet extends HttpServlet{
	
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//get parameters from post request
		String pnum = request.getParameter("pnum");
		String pname = request.getParameter("pname");
		String color = request.getParameter("color");
		String weight1 = request.getParameter("weight");
		String city = request.getParameter("city");
		try {
			
			
			if(weight1.length() != 0) {	//base case check if the user has not entered data
		//cast string into int
			int weight = Integer.parseInt(weight1);
			
			String rowsReturnedData = "";
			
			//number of rows effected
			int rs = 0;
	
			String query = "insert into parts values (?,?,?,?,?);";
			String reccord = "";
			
			getDBConnection();
			
			
				
				statement = connection.prepareStatement(query);
				
				statement.setString(1, pnum);
				statement.setString(2, pname);
				statement.setString(3, color);
				statement.setInt(4, weight);
				statement.setString(5, city);
				
				rs = statement.executeUpdate();
				
				reccord = "(" + pnum +"," + pname + "," + color + "," + weight +"," + city + ")";
				
				rowsReturnedData = "New parts reccord: " + reccord + " Sucessfully entered into the database" + "\n";
				
				//rowsReturnedData += "update sucessful. " + rs + " rows inserted";
				
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
			
			

			
			
			
		}catch(SQLException e) {
			
			
			//e.printStackTrace();
			
			
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
