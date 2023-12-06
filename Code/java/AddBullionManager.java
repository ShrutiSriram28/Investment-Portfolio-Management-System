import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class AddBullionManager extends HttpServlet {  
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
        String username = request.getParameter("username"); 
        out.println("<!DOCTYPE html>"+
        "<html lang=\"en\">"+
        "<head>"+
            "<meta charset=\"UTF-8\">"+
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
            "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">"+
            "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>"+
            "<link href=\"https://fonts.googleapis.com/css2?family=Tomorrow&display=swap\" rel=\"stylesheet\">"+
            "<title>Add Bullion</title>"+
            "<link rel=\"stylesheet\" href=\"css/style.css\">"+
            "<script src=\"js/AddBullionScript.js\"></script>"+
        "</head>"+
        "<body style=\"background-color: rgb(120, 120, 255);\">"+
            "<div style=\"position: absolute; left: 10vw; top: 10vh; width: 80vw; height: 80vh; display: flex; flex-direction: column; justify-content: space-evenly; align-items: center; background-color: white; border: 5px solid rgb(120, 120, 255); border-radius: 10px;\">"+
                "<form action=\"http://localhost:7071/Miniproject/java/InsertBullionManager\" method=\"get\" style=\"width:100%; height: 100%;\">"+
                    "<br>"+
                    "<h1 align=\"center\" style=\"font-size: 2.7em; text-align: center;\"> ADD BULLION</h1>"+
                    "<br><br><br><br>"+
                    "<div style=\"position: absolute; left: 4vw; width: 40%; height: 50%;\">"+
                        "<label for=\"scheme\"><b>Scheme</b></label><br>"+
                        "<input type=\"text\" list=\"scheme_list\" id = \"scheme\" name=\"scheme\" placeholder=\"Scheme\" oninput=\"autofill_scheme()\" onblur=\"fetch_price()\" required><br><br><br><br>"+
                        "<datalist id=\"scheme_list\">"+
                        "</datalist>"+
        
                        "<label for=\"purchase_price\"><b>Investment Price per Gram</b></label><br>"+
                        "<input type=\"text\" id = \"purchase_price\" name=\"purchase_price\" placeholder=\"Investment Price\" required><br><br><br><br>"+

                        "<label for=\"price\"><b>Current Price per Gram</b></label><br>"+
                        "<input type=\"text\" id = \"price\" name=\"price\" required><br><br><br><br>"+
                    "</div>"+
                    "<div style=\"position: absolute; left: 44vw; width: 40%; height: 50%;\">"+
                        "<label for=\"quantity\"><b>Quantity in Grams</b></label><br>"+
                        "<input type=\"text\" id = \"quantity\" name=\"quantity\" placeholder=\"Quantity\" onblur=\"calculate_net_amount()\" required><br><br>"+
                        
                        "<p id=\"validate_quantity\" style=\"color:red;\"><br><br></p>"+
        
                        "<label for=\"net\"><b>Net Amount</b></label><br>"+
                        "<input type=\"text\" id = \"net\" name=\"net\" required><br><br><br><br>"+
                        
                        "<input type=\"submit\" id=\"add\" value=\"Add\">"+  
                    "</div>"+ 
                    "<input type=\"hidden\" id=\"username\" name=\"username\" value="+username+">"+
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value="+status+">"+
                    "<p id=\"price_hidden\"></p>"+
                "</form>"+
                
                "<form action=\"http://localhost:7071/Miniproject/java/CancelRedirectUsernameManager\">"+
                    "<input type=\"hidden\" id=\"status1\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"username1\" name=\"username\" value="+username+">"+
                    "<input type=\"submit\" onclick=\"cancel()\" id=\"cancel\" style=\"position:absolute; left: 65vw; top:60vh;\" value=\"Cancel\">"+ 
                "</form>"+
            "</div>"+
        
        "</body>"+
        "</html>");
    }
}   