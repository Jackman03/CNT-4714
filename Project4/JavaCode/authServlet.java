/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Main authencitation file
*/


import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


@SuppressWarnings("serial")
public class authServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//this is where we will do our verification
		String username = request.getParameter("uname");
		String password = request.getParameter("pword");
		
		
	
		
		String contextPath = request.getContextPath();
		String destination = "";
		String redirectURL = "";
		
		String fileName = "C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project-4/WEB-INF/credentials.csv/";	
		String line = "";
		//read in values from text file of username and passwords
		

		response.setContentType( "text/html" );
		
		PrintWriter out = response.getWriter();
		
		//this is our genius encryption method
		
		
		//read in credinats csv
		int user = -1;
		
		boolean validUser = false;
		
		BufferedReader readIn = new BufferedReader(new FileReader(fileName));
		
		line = readIn.readLine();
		
		while(line!= null) {
			
			String lineValue[] = line.split(",");
			
			if(username.equals(lineValue[0]) && password.equals(lineValue[1])) {
				
				//this will be used to identiffy what user is logging in
				user = Integer.parseInt(lineValue[2]);
				validUser = true;
				break;
			}
			
			line = readIn.readLine();
			
		}
		
		
		
		
		readIn.close();
		
		if(validUser) {	//if the user has a valid username or password
			
			switch(user){
			
				case 1:	//root user
					
					destination = "/rootHome.jsp";
									
					break;
					
				case 2:	//client user
					
					destination = "/clientHome.jsp";
	
					break;
				
				case 3:	//data entry user
					
					destination = "/dataEntryHome.jsp";
	
					break;
					
				case 4:	//accountant user
					
					destination = "/accountantHome.jsp";
	
					break;
					
				
				}
				
			
				redirectURL = response.encodeRedirectURL(contextPath + destination);
				response.sendRedirect(redirectURL);
				
		}
		
		else {	//if the user enters and incorrect username or password
			
			destination = "/errorPage.html";
			redirectURL = response.encodeRedirectURL(contextPath + destination);
			response.sendRedirect(redirectURL);
			
		}
			
	
	}
	
}
