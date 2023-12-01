/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the data user to enter jobs
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mysql.cj.jdbc.MysqlDataSource;

@SuppressWarnings("serial")
public class jobEntryServlet extends HttpServlet{
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jnum = request.getParameter("jnum");
		String jname = request.getParameter("jname");
		String numworkers = request.getParameter("numworkers");	//convert to int later
		String city = request.getParameter("city");
		
		int rs = 0;
		
		String rowsReturnedData = "";
		try {
			
			if(numworkers.length() != 0) {	//base case to see if the user has entered faulty data
				
			
		
			int numworkers2 = Integer.parseInt(numworkers);
			
			String query = "insert into jobs values (?,?,?,?);";
			
			getDBConnection();
			
			String reccord = "";
			
			
				statement = connection.prepareStatement(query);
				
				
				statement.setString(1, jnum);
				statement.setString(2, jname);
				statement.setInt(3, numworkers2);
				statement.setString(4, city);
				
				reccord = "(" + jnum + "," + jname + "," + numworkers2 + "," + city +")";
				
				rs = statement.executeUpdate();
				
				//rowsReturnedData += "update sucessful. " + rs + " rows inserted";
				rowsReturnedData = "New jobs reccord: " + reccord + " Sucessfully entered into the database" + "\n";
				
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

			
			
			
		}catch(SQLException e) {
			
			
			String errorMessage = "SQL error "+e.getMessage() + statement;
			
			
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
