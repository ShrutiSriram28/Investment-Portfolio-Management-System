<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Tomorrow&display=swap" rel="stylesheet">
    <title>Add Stocks</title>
    <style>
        * {
            font-family: Tomorrow;
            margin: 0;
            padding: 0;
            border: 0;
            box-sizing: border-box;
        }

        input[type="text"] {
            width:100%; 
            height:5vh; 
            background-color: rgb(180, 180, 255); 
            border: 2px solid white; 
            border-radius: 7.5px;
        }

        input[type="submit"] {
            background-color:rgb(30, 180, 30);
            color: white;
            width:10vw;
            height:7vh;
            border: 2px solid rgb(30, 180, 30);
            border-radius: 5px;
            box-shadow: 2px 2px 7px rgb(30, 180, 30);
        }

        input[type="submit"]:hover {
            transform: translate(5px, 3px);
            box-shadow: 2px 2px 4px rgb(30, 180, 30);
        }

        button {
            background-color: rgb(180, 30, 30);
            color: white;
            width:10vw;
            height:7vh;
            border: 2px solid rgb(180, 30, 30);
            border-radius: 5px;
            box-shadow: 2px 2px 7px rgb(180, 30, 30);
        }

        button:hover {
            transform: translate(5px, 3px);
            box-shadow: 2px 2px 4px rgb(180, 30, 30);
        }
    </style>

    <script>
        function autofill_scheme() {
            var xhttp;
            var v=document.getElementById("scheme").value;  
            var url="java/GetScheme?scheme="+v;  
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var val=xhttp.responseText;  
                    document.getElementById('scheme_list').innerHTML=val;  
                }
            };
            xhttp.open("GET",url,true);
            xhttp.send();
        }

        function fetch_price() {
            var xhttp;
            var v=document.getElementById("scheme").value;  
            var url="java/GetStockPrice?scheme="+v;  
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var val=xhttp.responseText;  
                    document.getElementById('price_hidden').innerHTML=val;
                    set_price();  
                }
            };
            xhttp.open("GET",url,true);
            xhttp.send();
        }

        function set_price() {
            document.getElementById('price').value = document.getElementById('hidden_price').value;
        }

        function reset() {
            document.getElementById("scheme").value="";
            document.getElementById("price").value="";
            document.getElementById("quantity").value="";
            document.getElementById("net").value="";
        }

        function cancel() {
            reset();
            window.location="jsp/ViewPortfolio.jsp";
        }

        function calculate_net_amount() {
            window.alert(document.getElementById("status").value);
            var quantity = parseInt(document.getElementById("quantity").value);
            if(quantity < 0)
            {
                document.getElementById("validate_quantity").innerHTML = "Quantity cannot be negative<br><br>";
            }
            else
            {
                var price = parseInt(document.getElementById("price").value);
                document.getElementById("net").value = price * quantity;
                document.getElementById("validate_quantity").innerHTML = "<br><br>";
            }
        }
    </script>
</head>
<body style="background-color: rgb(120, 120, 255);">
    <div style="position: absolute; left: 10vw; top: 10vh; width: 80vw; height: 80vh; display: flex; flex-direction: column; justify-content: space-evenly; align-items: center; background-color: white; border: 5px solid rgb(120, 120, 255); border-radius: 10px;">
        <form action="http://localhost:7071/Miniproject/java/AddStocksClass" method="get" style="width:100%; height: 100%;">
            <br>
            <h1 align="center" style="font-size: 2.7em; text-align: center;"> ADD STOCK</h1>
            <br><br><br><br>
            <div style="position: absolute; left: 4vw; width: 40%; height: 50%;">
                <label for="scheme"><b>Scheme</b></label><br>
                <input type="text" list="scheme_list" id = "scheme" name="scheme" placeholder="Scheme" oninput="autofill_scheme()" onblur="fetch_price()" required><br><br><br><br>
                <datalist id="scheme_list">
                </datalist>

                <label for="price"><b>Investment Price</b></label><br>
                <input type="text" id = "price" name="price" required><br><br><br><br>

                <input type="submit" style="position:absolute; left: 21vw;" value="Add">  
            </div>
            <div style="position: absolute; left: 44vw; width: 40%; height: 50%;">
                <label for="quantity"><b>Quantity</b></label><br>
                <input type="text" id = "quantity" name="quantity" placeholder="Quantity" onblur="calculate_net_amount()" required><br><br>
                
                <p id="validate_quantity" style="color:red;"><br><br></p>

                <label for="net"><b>Net Amount</b></label><br>
                <input type="text" id = "net" name="net" required><br><br><br><br>

                <button onclick="cancel()">Cancel</button> 
            </div> 
            <p id="price_hidden"></p>

            <!--Just to store username-->
            <%= String value = request.getAttribute("status"); value = value.split("="); %>
            <input type="text" id="status" value="${value}">
            <script>
            window.alert("<%= request.getQueryString() %>");
            </script>
        </form>
    </div>

</body>
</html>