<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Client homepage</title>

        
        <link rel="stylesheet" type="text/css" href="css/style.css">


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
    String clientQuery = (String) session.getAttribute("clientQuery");

    String rowsReturnedClient = (String) session.getAttribute("rowsReturnedClient");

    if (rowsReturnedClient ==null) rowsReturnedClient = " ";

%>
    <div class = "main_area">

        <h1>Welcome to the fall 2023 Project 4 Enterprise System!</h1> 

        <p>you are connected to the project 4 enterprise database as a <FONT COLOR="RED">Client</FONT> user</p>


        <p>Please enter a query!</p><br>

        <form action = "/Project-4/clientServlet" method = "post">

        <textarea id="clientQuery" name="clientQuery" rows="4" cols="50"></textarea>

            <br>

            <input type = "submit" value = "Execute Query" />

            <input type="reset" value="Reset Form">

            <button onClick=""remove()>Clear Results</button>

        </form>

        <p>Query Results</p><br><br>

        <div class = "table_area">


            <table id = "data">

            

                <%=rowsReturnedClient%>
    
            </table>

        </div>

       

    </div>

    <script>
        function remove(){
            $("#data".remove());
        }
    </script>

     

    
    
</body>
</html>