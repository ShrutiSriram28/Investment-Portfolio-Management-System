<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Tomorrow&display=swap" rel="stylesheet">
    <title>Login</title>

    <style>
        * {
            font-family: Tomorrow;
            margin: 0;
            padding: 0;
            border: 0;
            box-sizing: border-box;
        }
        
        input[type="email"], input[type="password"], input[type="text"] {
            width:50%; 
            height:5vh; 
            background-color: rgb(180, 180, 255); 
            border: 2px solid white; 
            border-radius: 7.5px;
        }

        input[type="submit"], #ack_status {
            background-color: black;
            color: white;
            width:10vw;
            height:7vh;
            border: 2px solid black;
            border-radius: 25px;
            box-shadow: 2px 2px 7px rgb(50, 50, 50);
        }

        input[type="submit"]:hover, #ack_status:hover {
            transform: translate(5px, 3px);
            box-shadow: 2px 2px 4px rgb(69, 69, 69);
        }

        a {
            color:rgb(80, 80, 255); 
            text-decoration: none;
        }

        .swipe {
            text-shadow: 2px 2px 5px rgb(50, 50, 50);
        }

        .swipe:hover {
            text-shadow: 1px 1px 3px rgb(69, 69, 69);
            transform: translate(2px, 2px);
        }

        @keyframes slide_down {
            100% {
                transform: translate(0px, 10px);
            }
        }
    </style>

    <script>
        function swipe_left() {
            document.getElementById("investor").style.display = "none";
            document.getElementById("manager").style.left = "0vw";
            document.getElementById("manager").style.display = "block";
            document.getElementById("container").style.top = "15vh";
        }

        function swipe_right() {
            document.getElementById("manager").style.display = "none";
            document.getElementById("investor").style.left = "0vw";
            document.getElementById("investor").style.display = "block";
            document.getElementById("container").style.top = "15vh";
        }

        function change_focus(element) {
            element.style.backgroundColor = "rgb(219, 219, 255)";
        }
        
        function revert(element) {
            element.style.backgroundColor = "rgb(180, 180, 255)";
        }
    </script>
</head>
<body style="width:100%; height:100%;">
    <div style="display:flex; flex-direction: row; align-items: center;">
        <div style="width:40vw; height: 100vh; background-color:rgb(80, 80, 255); color:white; display: flex; align-items: center; justify-content: center; flex-direction: column;">
            <p style="font-size: 3em;"><b>IPMS</b></p>
            <p style="font-size: 1em;">Investment Portfolio Management System</p>
        </div>
        
        <!------------------------------------------------------------------------------------------------------------------------------------------------------>

        <div id="container" style="width:60vw; position:absolute; left:41vw; display: flex; align-items: center; flex-direction: column;">
            
            <div id="notify" style="display:flex; 
            flex-direction: column; 
            justify-content: space-evenly; 
            align-items: center; 
            background-color: white; 
            height:75vh; 
            width: 30vw; 
            box-shadow:5px 5px 10px rgb(180, 180, 255), -5px -5px 10px rgb(180, 180, 255); 
            border: 2px solid white;
            border-radius: 10px;
            display:none;">

                <div id="status_img_div" style="border-radius:50%; width: 100px; height: 100px;">
                    <img id="status_img" style="width: 100%; height: 100%;" src="">
                </div>
                <p id="status_p" style="text-align: center; font-size: 2em; font-weight: 700;"></p>
                <button id="ack_status" onclick="acknowledge()">OK</button>
            </div>
            
            <fieldset id="investor" align="center" style="width:100%; height:100%; border:0;">
                <br>
                <h1 align="center" style="font-size: 3em; text-align: center;">INVESTOR LOGIN</h1>
                <br><br>
                <form action="http://localhost:7071/Miniproject/java/InvestorLoginValidate" method="get">
                    <label for="email"><b>Email</b></label><br><br>
                    <input type="email" id = "email" name="email" onfocus="change_focus(this)" onblur="revert(this)" placeholder="Email" required><br><br><br>
                    
                    <label for="password"><b>Password</b></label><br><br>
                    <input type="password" id = "password" name="password" onfocus="change_focus(this)" onblur="revert(this)" placeholder="Password" required><br><br><br>
                        
                    <input type="submit" value="Investor Login">  
                </form>    
                <br><br>

                <p><i><b>Don't have an account? </b></i><a href="jsp/SignUp.jsp"><b> Sign Up</b></a></p>

                <button class="swipe" onclick="swipe_left()" style="font-size: 3em; position: absolute; top: 30vh; left: 51vw; background: none;">>></button>
                <p style="font-size: 0.8em; position: absolute; top: 39vh; left: 51vw;"><b>Manager<br>Login</b></p>
            </fieldset>

            <!------------------------------------------------------------------------------------------------------------------------------------------------------>

            <input id="status" type="hidden" value="<%= request.getAttribute("status") %>">
            <script>
                var status = document.getElementById("status").value;
                
                if(status == "incorrect")
                {
                    document.getElementById("notify").style.display = "flex";
                    document.getElementById("status_p").innerHTML = "Incorrect Username <br> or Password!";
                    document.getElementById("status_img").src = "images/cross.png";
                    document.getElementById("investor").style.display = "none";
                    document.getElementById("notify").style.animation = "slide_down 2s ease-out 1 forwards";
                }

                function acknowledge() {
                    document.getElementById("investor").style.display = "block";
                    document.getElementById("notify").style.display = "none";
                    document.getElementById("status").value = "";
                }
            </script>

            <!------------------------------------------------------------------------------------------------------------------------------------------------------>

            <fieldset id="manager" align="center" style="width:100%; height:100%; border:0; position: absolute; left: 100vw; display:none;">
                <br>
                <h1 align="center" style="font-size: 3em; text-align: center;">MANAGER LOGIN</h1>
                <br><br>
                <form action="http://localhost:7071/Miniproject/java/InvestmentManagerLoginValidate" method="get">
                    <label for="email_manager"><b>Email</b></label><br><br>
                    <input type="email" id = "email_manager" name="email_manager" onfocus="change_focus(this)" onblur="revert(this)" placeholder="Email" required><br><br><br>
                    
                    <label for="password_manager"><b>Password</b></label><br><br>
                    <input type="password" id = "password_manager" name="password_manager" onfocus="change_focus(this)" onblur="revert(this)" placeholder="Password" required><br><br><br>    
                        
                    <input type="submit" value="Manager Login">  
                </form>
                <br><br>

                <p><i><b>Don't have an account? </b></i><a href="jsp/SignUp.jsp"><b> Sign Up</b></a></p>
                
                <button class="swipe" onclick="swipe_right()" style="font-size: 3em; position: absolute; top: 30vh; left: 5vw; background: none;"><<</button>
                <p style="font-size: 0.8em; position: absolute; top: 39vh; left: 5vw;"><b>Investor<br>Login</b></p>
            </fieldset>

            <!------------------------------------------------------------------------------------------------------------------------------------------------------>
        </div>
    </div>
</body>
</html>