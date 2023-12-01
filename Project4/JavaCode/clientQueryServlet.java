/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for the client user to run queries
*/
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@SuppressWarnings("serial")
public class clientQueryServlet extends HttpServlet{
	
	private Connection connection = null;
	private Statement statement = null;
	private int updateValue;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		

		getDBConnection();
		//extract the client query
		String clientQuery = request.getParameter("clientQuery");
		String  rowsReturnedClient;
		updateValue = 0;
		//trim the string to protect aginst any sql injections or whatnot.
		clientQuery = clientQuery.trim();	
		ResultSet rs = null;
		int rowsUpdated = 0;
		
		

		try {
			
			if(clientQuery.length() != 0) {
			
			if(clientQuery.substring(0, 1).equalsIgnoreCase("s")) {
				rs = statement.executeQuery(clientQuery);
				ResultSetToHTML returnTable = new ResultSetToHTML();
				
				rowsReturnedClient = returnTable.getHTMLTable(rs);
				
				
				
				//get the session from the jsp page
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedClient", rowsReturnedClient);
				session.setAttribute("clientQuery", clientQuery);
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);  
			}
			
			else {
				//no use filling out the rest of the code since client will error
				rowsUpdated = statement.executeUpdate(clientQuery);
			}
			
			}
			
			else {
				HttpSession session = request.getSession();
				session.setAttribute("rowsReturnedClient", "Please enter a query!");
				session.setAttribute("clientQuery", "");
				
				//prepares the values to be sent
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
				
				//sends the values to the website
				dispatcher.forward(request, response);
			}
			
			
			
		}
		catch(Exception e) {
			String errorMessage = "SQL error "+e.getMessage();
			
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedClient", errorMessage);
			session.setAttribute("clientQuery", clientQuery);
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response);  

		}
	

	}
	
	
	private void getDBConnection() {
		
		try {
		//use a properties file
			String filePath = "C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project-4/WEB-INF/lib/client.properties/";
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
