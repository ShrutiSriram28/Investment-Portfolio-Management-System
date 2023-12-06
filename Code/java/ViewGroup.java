import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class ViewGroup extends HttpServlet {  
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
    
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute SQL query
            Statement stmt = conn.createStatement();
            String status = request.getParameter("status"); 
            String sql = "SELECT * FROM investor_login WHERE manager = '" + status + "'";
            ResultSet rs = stmt.executeQuery(sql);

            //---------------------------------------------------------------------------------------------------------------------------

            out.println("<!DOCTYPE html>"+
            "<html lang=\"en\">"+
            "<head>"+
                "<meta charset=\"UTF-8\">"+
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">"+
                "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>"+
                "<link href=\"https://fonts.googleapis.com/css2?family=Tomorrow&display=swap\" rel=\"stylesheet\">"+
                "<title>Display Group</title>"+
                "<link rel=\"stylesheet\" href=\"css/ViewPortfolioStyle.css\">"+
                "<script src=\"js/ViewPortfolioManagerScript.js\"></script>"+
            "</head>"+
            "<body>"+
                "<div class=\"navbar\">"+
                    "<div style=\"font-size:30px;cursor:pointer; color:white; position: absolute; left: 2vw; top: 2vh;\" onclick=\"slide()\">&#9776;</div>"+
                    "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;position: absolute; left: 83vw; width: 15vw; height: 100%;\">"+
                        "<img src=\"images/user.png\" width=\"50px\" height=\"50px\">"+
                        "<p id=\"username_status\" style=\"font-size:2em; font-weight:700;color: white;\">" + status + "</p>"+
                        "<div></div>"+
                    "</div>"+
                "</div>"+
                "<div id=\"side_panel\" class=\"sidenav\">"+
                    "<div style=\"display:flex; flex-direction: column; justify-content:space-around; align-items: center; width: 100%; height:85%;\">"+
                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+                        

                        "<form id=\"group_form\" action=\"http://localhost:7071/Miniproject/java/ViewGroup\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/group.png\" id=\"group_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+
                            "<input type=\"submit\" id=\"group\" value=\"Group\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+

                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+

                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+
                        
                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+

                        "<form id=\"logout_form\" action=\"http://localhost:7071/Miniproject/java/LogoutManager\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/logout.png\" id=\"logout_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value=" + status + ">"+
                            "<input type=\"submit\" id=\"logout\" value=\"Logout\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+

                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+
                    "</div>"+
                "</div>"+

                "<div style=\"position: absolute; justify-content: center; align-items:center; top: 10vh; left: 25vw; width: 60vw; display: flex; flex-direction: column;\">"+ 
                "<p style=\"color:rgb(80, 80, 255); font-size: 3em; background-color: white; position:fixed; top:10vh; width: 70%;text-align: center;\"><b>INVESTOR</b></p><br><br><br><br><br>");

            //---------------------------------------------------------------------------------------------------------------------------
            int flag = 0;
            int count = 0;
            while(rs.next())
            {
                int chat_flag = 0;
                Statement stmt1 = conn.createStatement();
                String sql1 = "SELECT * FROM chat WHERE username = '" + rs.getString("username") + "' AND manager = '" + status + "'";
                ResultSet rs1 = stmt1.executeQuery(sql1);

                if(rs1.next())
                {
                    chat_flag = 1;
                }
                stmt1.close();
                rs1.close();

                count += 1;
                out.println("<div style=\"width:70%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255);\">");
                out.print("<p style=\"display:inline;\"><br>Email: " + rs.getString("email") + "</p><br>");
                if(chat_flag == 1)
                {
                    out.print("<p style=\"display:inline; color:red; float:right;\"><br>Request Pending</p><br>");
                } 
                out.print("<p style=\"display: inline;\">Username: " + rs.getString("username") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/ViewAssets\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status_hidden" + count + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"email_hidden" + count + "\" name=\"email\" value="+rs.getString("email")+">"+
                    "<input type=\"hidden\" id=\"username_hidden" + count + "\" name=\"username\" value="+ rs.getString("username") +">"+
                    "<input type=\"submit\" id=\"view" + count + "\" value=\"View\" style=\"background-color: orange; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid orange; border-radius: 5px; box-shadow: 2px 2px 7px orange;\">"+ 
                "</form><br></div><br><br><br>");
                flag = 1;
            }
            if(flag == 0)
            {
                out.println("<div style=\"width:80%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255); display:flex; justify-content: center; align-items:center;\">");
                out.print("<p>None Added!</p><br></div><br><br><br>");
            } 

            out.println("</div><div style=\"position: fixed; justify-content: space-evenly; align-items:center; top: 10vh; left: 85vw; width: 12vw; height: 50vh; display: flex; flex-direction: column;\">");
            out.println("<form action=\"http://localhost:7071/Miniproject/java/AddMember\" method=\"get\">" +
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+ 
                    "<input type=\"submit\" value=\"ADD MEMBER\" style=\"background-color: green; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid green; border-radius: 5px; box-shadow: 2px 2px 7px green;\">"+
                "</form></div></body></html>");

            
            stmt.close();
            rs.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}   