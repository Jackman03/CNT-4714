<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accountant Homepage</title>

    <link rel="stylesheet" type="text/css" href="css/style.css">

    <style>
  
        table, th, td {
          border: 1px solid darkblue;
          border-collapse: collapse;
          margin-left: auto; 
          margin-right: auto;
        }

        label{
            color: blue;
        }

        end{
            color: red;
        }

    </style>
    
</head>
<body>

    <%

    String rowsReturnedAccountant = (String) session.getAttribute("rowsReturnedAccountant");

    if (rowsReturnedAccountant ==null) rowsReturnedAccountant = " ";

    %>


    <div class = "main_area">


        <h1>Welcome to the fall 2023 Project 4 Enterprise System!</h1> 

        <p>you are connected to the project 4 enterprise database as a <FONT COLOR="RED">Accountant</FONT> user</p>

        <p>Please select the option you would like to preform from the list below</p>


        <form action = "/Project-4/accountantServlet" method = "post">

            <div class = "list">

          

                <label><input type="radio" name="options" value="max_supplier"> 
                    Get the maximum status value for all suppliers <end>(Returns a maximum value)</end>
                </label><br><br>


                <label><input type="radio" name="options" value="weight_parts"> 
                    Get the total weight of all parts <end>(Returns a sum)</end>
                </label><br><br>


                <label><input type="radio" name="options" value="total_shipments"> 
                    Get the total number of shipments <end>(Returns the current number of shipments in total)</end>
                </label><br><br>


                <label><input type="radio" name="options" value="most_workers"> 
                    Get the name and number of workers of the job with the most workers <end>(Returns 2 values)</end>
                </label><br><br>


                <label><input type="radio" name="options" value="supplier_stat"> 
                    Get the name and status of every supplier <end>(Returns a list of supplier names with status)</end>
                </label><br><br>


                <input type = "submit" value = "Execute command">
                <button onClick=""remove()>Clear results</button>


            </div>



        </form>



        <div class = "table_area">


            <table id = "data">

            

                <%=rowsReturnedAccountant%>
    
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