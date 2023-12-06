import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class Logout extends HttpServlet {  
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
            "<title>Logout</title>"+
            "<link rel=\"stylesheet\" href=\"css/style.css\">"+
            "<script src=\"js/Script.js\"></script>"+
        "</head>"+
        "<body style=\"background-color: rgb(120, 120, 255);\">"+
            "<div style=\"position: absolute; left: 20vw; top: 20vh; width: 60vw; height: 60vh; display: flex; flex-direction: row; justify-content: space-evenly; align-items: center; background-color: white; border: 5px solid rgb(120, 120, 255); border-radius: 10px;\">"+
                "<br>"+
                "<h1 align=\"center\" style=\"font-size: 2.7em; text-align: center;\"> LOGOUT?</h1>"+
                "<br><br><br><br>"+
                "<form action=\"http://localhost:7071/Miniproject/jsp/Login.jsp\" method=\"get\">"+
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value="+status+">"+
                    "<input type=\"submit\" id=\"add\" value=\"Yes\">"+   
                "</form>"+
                
                "<form action=\"http://localhost:7071/Miniproject/java/CancelRedirectUsername\">"+
                    "<input type=\"hidden\" id=\"status1\" name=\"status\" value="+status+">"+
                    "<input type=\"submit\" id=\"cancel\" value=\"No\">"+ 
                "</form>"+
            "</div>"+
        
        "</body>"+
        "</html>");
    }
}   