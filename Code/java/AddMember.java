import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class AddMember extends HttpServlet {  
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost:3306/Miniproject";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "mysql";
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException{  
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        String status = request.getParameter("status"); 
        out.println("<!DOCTYPE html>"+
        "<html lang=\"en\">"+
        "<head>"+
            "<meta charset=\"UTF-8\">"+
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
            "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">"+
            "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>"+
            "<link href=\"https://fonts.googleapis.com/css2?family=Tomorrow&display=swap\" rel=\"stylesheet\">"+
            "<title>Add Investor</title>"+
            "<link rel=\"stylesheet\" href=\"css/style.css\">"+
            "<script src=\"js/AddMemberScript.js\"></script>"+
        "</head>"+
        "<body style=\"background-color: rgb(120, 120, 255);\">"+
            "<div style=\"position: absolute; left: 20vw; top: 20vh; width: 60vw; height: 60vh; display: flex; flex-direction: column; justify-content: space-evenly; align-items: center; background-color: white; border: 5px solid rgb(120, 120, 255); border-radius: 10px;\">"+
                "<form action=\"http://localhost:7071/Miniproject/java/InsertMember\" method=\"get\" style=\"width:100%; height: 100%;\">"+
                    "<br>"+
                    "<h1 align=\"center\" style=\"font-size: 2.7em; text-align: center;\"> ADD INVESTOR</h1>"+
                    "<br><br>"+
                    "<div style=\"position: absolute; left: 4vw; top: 25vh; width: 40%; height: 50%;\">"+
                        "<label for=\"username\"><b>Username</b></label><br>"+
                        "<input type=\"text\" id = \"username\" name=\"username\" placeholder=\"Username\" onblur=\"check_availability()\" required><br><br>"+
                        "<p id=\"validate_member\" style=\"color: red;font-size:1em;\"></p>"+
                    "</div>"+                      
                    "<input type=\"submit\" id=\"add\" value=\"Add\" style=\"position:absolute; left: 30vw; top:26vh;\">"+   
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value="+status+">"+
                    "<p id=\"price_hidden\"></p>"+
                "</form>"+
                
                "<form action=\"http://localhost:7071/Miniproject/java/CancelRedirectUsernameManager\">"+
                    "<input type=\"hidden\" id=\"status1\" name=\"status\" value="+status+">"+
                    "<input type=\"submit\" id=\"cancel\" style=\"position:absolute; left: 45vw; top:26vh;\" value=\"Cancel\">"+ 
                "</form>"+
            "</div>"+
        
        "</body>"+
        "</html>");
    }
}   




