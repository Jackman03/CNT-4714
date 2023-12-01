<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Data entry</title>

    <link rel="stylesheet" type="text/css" href="./css/style.css"/>

    <style>
  
        table, th, td {
          border: 1px solid darkblue;
          border-collapse: collapse;
          margin-left: auto; 
          margin-right: auto;
        }

    </style>


</head>
<body>

    <%

    String rowsReturnedData = (String) session.getAttribute("rowsReturnedData");

    if (rowsReturnedData ==null) rowsReturnedData = " ";

    %>

    <div class = "main_area">

        <h1>Welcome to the fall 2023 Project 4 Enterprise System!</h1> 

        <h2>Data Entry Application</h2>

        <p>you are connected to the project 4 enterprise database as a <FONT COLOR="RED">Data entry</FONT> user</p>
        <p>Please enter data values into the forms below to add a new reccord to a corosponding database table.</p>


        <div class = "input_box">

            <p>Suppliers reccord insert</p>

            <form action = "/Project-4/supplierServlet" method = "post">

            <table id = "suppliers">
                <tr>
                    <td>snum</td>   <td>sname</td>  <td>status</td> <td>city</td>
                </tr>
                <tr>
                    <td><input type="text" name="snum"></td>    <td><input type="text" name="sname"></td>    
                    <td><input type="text" name="status"></td>    <td><input type="text" name="city"></td>
                </tr>

                
            </table><br>

            <input type = "submit" value = "Enter supplier reccord into database" /> 
            <input type = "reset" value = "Clear reccord" />


            </form>

        </div><br>

        <div class = "input_box">

            <p>Parts reccord insert</p>

            <form action = "/Project-4/partServlet" method = "post">

            

            <table id = "parts">
                <tr>
                    <td>pnum</td>   <td>pname</td>  <td>color</td>  <td>weight</td> <td>city</td>
                </tr>
                <tr>
                    <td><input type="text" name="pnum"></td>    <td><input type="text" name="pname"></td>    
                    <td><input type="text" name="color"></td>   <td><input type="text" name="weight"></td>   
                    <td><input type="text" name="city"></td>
                </tr>

                
            </table><br>

            
            <input type = "submit" value = "Enter part reccord into database" /> 
            <input type = "reset" value = "Clear reccord" />

            </form>

        </div><br>

        

        <div class = "input_box">

            <p>Jobs reccord insert</p>

            <form action = "/Project-4/jobServlet" method = "post">

            <table id = "jobs">
                <tr>
                    <td>jnum</td>   <td>jname</td>  <td>numworkers</td> <td>city</td>
                </tr>
                <tr>
                    <td><input type="text" name="jnum"></td>    <td><input type="text" name="jname"></td>    
                    <td><input type="text" name="numworkers"></td>    <td><input type="text" name="city"></td>
                </tr>

                
            </table><br>

            <input type = "submit" value = "Enter job reccord into database" /> 
            <input type = "reset" value = "Clear reccord" />

            </form>

        </div><br>

        <div class = "input_box">

            <p>Shipments reccord insert</p>

            <form action = "/Project-4/shipmentServlet" method = "post">

            <table id = "shipments">
                <tr>
                    <td>snum</td>   <td>pnum</td>  <td>jnum</td> <td>quantity</td>
                </tr>
                <tr>
                    <td><input type="text" name="snum"></td>    <td><input type="text" name="pnum"></td>    
                    <td><input type="text" name="jnum"></td>    <td><input type="text" name="quantity"></td>
                </tr>

                
            </table><br>

            <input type = "submit" value = "Enter shipment reccord into database" /> 
            <input type = "reset" value = "Clear reccord" />

            </form>

        </div><br>
        
        <%=rowsReturnedData%>


    </div>
</body>
</html>