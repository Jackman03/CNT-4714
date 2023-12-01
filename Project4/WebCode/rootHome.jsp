<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Root homepage</title>

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

    
    String rootQuery = (String) session.getAttribute("rootQuery");

    String rowsReturnedRoot = (String) session.getAttribute("rowsReturnedRoot");

    if (rowsReturnedRoot ==null) rowsReturnedRoot = " ";

    

%>
    <div class = "main_area">

        <h1>Welcome to the fall 2023 Project 4 Enterprise System!</h1>

        <p>you are connected to the project 4 enterprise database as a <FONT COLOR="RED">Root</FONT> user</p>


        <p>Please enter a query!</p><br>

        <form action = "/Project-4/rootServlet" method = "post">

        <textarea id="rootQuery" name="rootQuery" rows="4" cols="50"></textarea>

            <br>

            <input type = "submit" value = "Execute Query" />

            <input type="reset" value="Reset Form">

            <button onClick=""remove()>Clear Results</button>


            

        </form>

        <p>Query Results</p><br><br>

        <div class = "table_area">


            <table id = "data">

            

                <%=rowsReturnedRoot%>
    
            </table>

        </div>

        <script>
            function remove(){
                $("#data".remove());
            }
        </script>

       

    </div>

     

    
    
</body>
</html>