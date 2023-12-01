/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
Helper class to convery result sets to html tables
*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class ResultSetToHTML {
	
	
	public static synchronized String getHTMLTable(ResultSet resultSet) {
		

		String table = "";
	
		try {
			
			ResultSetMetaData RSMD = (ResultSetMetaData) resultSet.getMetaData();	
			
			//Vector<String> columnNames = new Vector<String>();
			
			int numCols = 0;
			//number of colums returned from query
			numCols = RSMD.getColumnCount();
			
			//create table header
			table += "<tr>";
			for(int i = 0; i < numCols ; i++) {
		        //create one big string of colums
				
				table +="<th>" + RSMD.getColumnName(i+1) + "</th>";
				

			}
			table += "</tr>";
			numCols = RSMD.getColumnCount();
			
			while(resultSet.next()) {
				//loop through rach row
				
				//add table start
				table +="<tr>";
				
				//look throuch each colum
				for(int i = 0; i < numCols; i ++) {
					table += "<td>" + resultSet.getString(i+1) + "</td>";					
				}
				//add table end
				table +="</tr>";
			}
			
			//this adds one row to the table. now we do it again for the data
			
			//we reutn the string header now
			
			return table;
			
		}
		//create a vector of all of the colum names
		
		
		
		catch(SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		
		
	}

}
