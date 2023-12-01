/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Servlet for accountant user
*/


import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mysql.cj.jdbc.MysqlDataSource;
@SuppressWarnings("serial")
public class accountantServlet extends HttpServlet{
	
	private Connection connection = null;
	private CallableStatement  statement = null;
	
	
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//execute various stored proecdures
		
		String max_supplier = "Get_The_Maximum_Status_Of_All_Suppliers()}";
		String weight_parts = "Get_The_Sum_Of_All_Parts_Weights()}";
		String total_shipments = "Get_The_Total_Number_Of_Shipments()}";
		String most_workers = "Get_The_Name_Of_The_Job_With_The_Most_Workers()}";
		String supplier_stat = "List_The_Name_And_Status_Of_All_Suppliers()}";
		
		ResultSet rs = null;
		
		String rowsReturnedAccountant = "";
		
		getDBConnection();
		
		String value ="";
		
		value =  request.getParameter("options");
		try {
		
		if(value != null) {
			
			
			switch(value) {
				
			case "max_supplier":
				statement = connection.prepareCall("{call " + max_supplier);
				break;
				
				
			case "weight_parts":
				statement = connection.prepareCall("{call " + weight_parts);
				break;
				
			case "total_shipments":
				statement = connection.prepareCall("{call " + total_shipments);
				break;
				
				
			case "most_workers":
				statement = connection.prepareCall("{call " + most_workers);
				break;
				
				
			case "supplier_stat":
				statement = connection.prepareCall("{call " + supplier_stat);
				break;
				
			}
			
			//statement executes
			//we need to convert this statment into a result set
			statement.execute();
			
			rs = statement.getResultSet();
			
			
			ResultSetToHTML returnTable = new ResultSetToHTML();
			
			rowsReturnedAccountant = returnTable.getHTMLTable(rs);
			
			
		
			//rowsReturned = "the value is:" + value;
			
			//get the session from the jsp page
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedAccountant", rowsReturnedAccountant);
		
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response); 
			
		}
		
		else {
			
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedAccountant", "");
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response);
			
		}
		 
			
			
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String errorMessage = "SQL error "+e.getMessage();
			
			HttpSession session = request.getSession();
			session.setAttribute("rowsReturnedAccountant", errorMessage);
	
			
			//prepares the values to be sent
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
			
			//sends the values to the website
			dispatcher.forward(request, response);  
		}
		
		
		
		
	}
	
	
	@SuppressWarnings("unused")
	private void getDBConnection() {
		
		try {
		//use a properties file
			String filePath = "C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project-4/WEB-INF/lib/accountant.properties/";
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
