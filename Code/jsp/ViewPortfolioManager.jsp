<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Tomorrow&display=swap" rel="stylesheet">
    <title>Display Portfolio</title>
    <style>    
        * {
            font-family: Tomorrow;
            border: 0;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .navbar {
            width:100%;
            height: 10vh;
            position: fixed;
            z-index: 1;
            top: 0vh;
            left: 0;
            background-color: rgb(120, 120, 255);
            overflow-x: hidden;
            transition: 0.5s;
        }

        .sidenav {
            height: 90vh;
            width: 7vw;
            position: fixed;
            z-index: 1;
            top: 10vh;
            left: 0;
            background-color: rgb(180, 180, 255);
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 5vh;
        }

        .sidenav input[type="submit"], .sidenav a {
            padding: 8px 8px 8px 32px;
            text-decoration: none;
            font-size: 1em;
            color: white;
            display: none;
            transition: 0.3s;
        }

        .sidenav .closebtn {
            position: absolute;
            top: 0;
            right: 25px;
            font-size: 36px;
            margin-left: 50px;
        }

        @media screen and (max-height: 450px) {
            .sidenav {padding-top: 15px;}
            .sidenav a {font-size: 1em;}
        }

        .hide {
            opacity: 0;
        }

        .letters {
            position: relative;
        }

        .letters span {
            position: relative;
            display: inline-block;
            font-size: 3em; 
            font-weight: 700; 
            color: rgb(80, 80, 255);
            text-transform: uppercase;
            animation: flip 4s infinite;
            animation-delay: calc(0.25s * var(--i));
            text-shadow: 0px 50px 20px rgb(120, 120, 255), -0px -50px 20px rgb(120, 120, 255);
        }

        @keyframes flip {
            0%,80% {
                transform: rotateY(360deg) 
            }
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div style="font-size:30px;cursor:pointer; color:white; position: absolute; left: 2vw; top: 2vh;" onclick="slide()">&#9776;</div>
        <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;position: absolute; left: 83vw; width: 15vw; height: 100%;">
            <img src="user.png" width="50px" height="50px">
            <p id="username_status" style="font-size:2em; font-weight:700;color: white;"></p>
            <div></div>
        </div>
    </div>
    <div id="side_panel" class="sidenav">
        <div style="display:flex; flex-direction: column; justify-content:space-around; align-items: center; width: 100%; height:85%;">
            <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
            </div>

            <form id="group_form" action="http://localhost:7071/Miniproject/java/ViewGroup" method="get" style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
                <img src="images/group.png" id="group_img" width="35px" height="35px">
                <input type="hidden" id="status" name="status" value="<%= request.getAttribute("status") %>">
                <input type="submit" id="group" value="Group" style="background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;">
            </form>
            
            <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
            </div>
            
            <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
            </div>

            <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
            </div>

            <form id="logout_form" action="http://localhost:7071/Miniproject/java/LogoutManager" method="get" style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
                <img src="images/logout.png" id="logout_img" width="35px" height="35px">
                <input type="hidden" id="status" name="status" value="<%= request.getAttribute("status") %>">
                <input type="submit" id="logout" value="Logout" style="background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;">
            </form>

            <div style="display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;">
            </div>
        </div>
    </div>

    <div class="letters" style="position: absolute; justify-content: center; align-items:center; top: 10vh; left: 25vw; width: 60vw; height: 80vh; display: flex; flex-direction: row;">
        <span style="--i:1">W</span>
        <span style="--i:2">E</span>
        <span style="--i:3">L</span>
        <span style="--i:4">C</span>
        <span style="--i:5">O</span>
        <span style="--i:6">M</span>
        <span style="--i:7">E</span>
        <span style="--i:8">&ensp;</span>
        <span style="--i:9">T</span>
        <span style="--i:10">O</span>
        <span style="--i:11">&ensp;</span>
        <span style="--i:12">I</span>
        <span style="--i:13">P</span>
        <span style="--i:14">M</span>
        <span style="--i:15">S</span>
        <span style="--i:16">!</span>
    </div>  

    

    <script>
        document.getElementById("status").value = "<%= request.getAttribute("status") %>";
        var status = document.getElementById("status").value;
        document.getElementById("username_status").innerHTML = status;
    </script>

    <script>
        var click = 0;

        function slide() {
            if(click == 0)
            {
                document.getElementById("side_panel").style.width = "25vw";
                click = 1;
                document.getElementById('group').style.display = "block";
                document.getElementById('logout').style.display = "block";
            }
            else 
            {
                document.getElementById("side_panel").style.width = "7vw";
                click = 0;
                document.getElementById('group').style.display = "none";
                document.getElementById('logout').style.display = "none";
            }
        }
    </script>
    
</body>
</html> 
