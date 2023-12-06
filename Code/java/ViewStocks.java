import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class ViewStocks extends HttpServlet {  
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
            String sql = "SELECT * FROM purchased_stocks WHERE username = '" + status + "'";
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
                "<title>Display Stocks</title>"+
                "<link rel=\"stylesheet\" href=\"css/ViewPortfolioStyle.css\">"+
                "<script src=\"js/ViewPortfolioScript.js\"></script>"+
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
                        "<form id=\"chat_form\" action=\"http://localhost:7071/Miniproject/java/Chat\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/request.png\" id=\"chat_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+
                            "<input type=\"submit\" id=\"chat\" value=\"Request\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+
                            
                        "<form id=\"stocks_form\" action=\"http://localhost:7071/Miniproject/java/ViewStocks\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/stocks.png\" id=\"stocks_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+
                            "<input type=\"submit\" id=\"stocks\" value=\"Stock\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+
                        
                        "<form id=\"mf_form\" action=\"http://localhost:7071/Miniproject/java/ViewMF\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/mf.png\" id=\"mf_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+
                            "<input type=\"submit\" id=\"mf\" value=\"Mutual Fund\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+

                        "<form id=\"bullion_form\" action=\"http://localhost:7071/Miniproject/java/ViewBullion\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/bullion.png\" id=\"bullion_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+
                            "<input type=\"submit\" id=\"bullion\" value=\"Bullion\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+

                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+
                        
                        "<div style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                        "</div>"+

                        "<form id=\"logout_form\" action=\"http://localhost:7071/Miniproject/java/Logout\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
                            "<img src=\"images/logout.png\" id=\"logout_img\" width=\"35px\" height=\"35px\">"+
                            "<input type=\"hidden\" id=\"status\" name=\"status\" value=" + status + ">"+
                            "<input type=\"submit\" id=\"logout\" value=\"Logout\" style=\"background:none; width: 12vw; height: 35px; font-weight: 700; text-align:start;\">"+
                        "</form>"+
                    "</div>"+
                "</div>"+

                "<div style=\"position: absolute; justify-content: center; align-items:center; top: 10vh; left: 25vw; width: 60vw; display: flex; flex-direction: column;\">"+ 
                "<p style=\"color:rgb(80, 80, 255); font-size: 3em; background-color: white; position:fixed; top:10vh; width: 70%;text-align: center;\"><b>PURCHASED STOCKS</b></p><br><br><br><br><br>");

            //---------------------------------------------------------------------------------------------------------------------------
            int flag = 0;
            float unrealised_gain = 0;
            int count = 0;
            while(rs.next())
            {
                count += 1;
                out.println("<div style=\"width:70%; height: 30vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255);\">");
                
                int quantity = Integer.parseInt(rs.getString("quantity"));
                float purchase_price = Float.parseFloat(rs.getString("purchase_price"));
                float price = Float.parseFloat(rs.getString("price"));
                float gain = (price * quantity - purchase_price * quantity);

                unrealised_gain += (price * quantity - purchase_price * quantity);
                
                out.print("<p style=\"display:inline;\">Scheme: " + rs.getString("scheme") + "</p><p style=\"display:inline;float:right; margin-right: 2vw;\">Gain: " + gain + "</p><br><br>");
                out.print("<p style=\"display: inline\">Purchase Price: " + rs.getString("purchase_price") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/SellStock\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status" + count + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"scheme_hidden" + count + "\" name=\"scheme\" value="+rs.getString("scheme")+">"+
                    "<input type=\"hidden\" id=\"purchase_price_hidden" + count + "\" name=\"purchase_price\" value="+ rs.getString("purchase_price") +">"+
                    "<input type=\"hidden\" id=\"price_hidden" + count + "\" name=\"price\" value="+ rs.getString("price") +">"+
                    "<input type=\"hidden\" id=\"quantity_hidden" + count + "\" name=\"quantity\" value="+ rs.getString("quantity") +">"+
                    "<input type=\"hidden\" id=\"net_hidden" + count + "\" name=\"net\" value="+ rs.getString("net") +">"+
                    "<input type=\"submit\" id=\"sell" + count + "\" value=\"Sell\" style=\"background-color: orange; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid orange; border-radius: 5px; box-shadow: 2px 2px 7px orange;\">"+ 
                "</form><br>");
                out.print("<p>Current Price: " + rs.getString("price") + "</p><br>");
                out.print("<p style=\"display: inline\">Quantity: " + rs.getString("quantity") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/DeleteStock\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status" + count + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"scheme_hidden" + count + "\" name=\"scheme\" value="+rs.getString("scheme")+">"+
                    "<input type=\"hidden\" id=\"purchase_price_hidden" + count + "\" name=\"purchase_price\" value="+ rs.getString("purchase_price") +">"+
                    "<input type=\"hidden\" id=\"price_hidden" + count + "\" name=\"price\" value="+ rs.getString("price") +">"+
                    "<input type=\"hidden\" id=\"quantity_hidden" + count + "\" name=\"quantity\" value="+ rs.getString("quantity") +">"+
                    "<input type=\"hidden\" id=\"net_hidden" + count + "\" name=\"net\" value="+ rs.getString("net") +">"+
                    "<input type=\"submit\" id=\"delete" + count + "\" value=\"Delete\" style=\"background-color: rgb(180, 30, 30); color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid rgb(180, 30, 30); border-radius: 5px; box-shadow: 2px 2px 7px rgb(180, 30, 30);\">"+ 
                "</form><br>");
                out.print("<p>Net Amount: " + rs.getString("net") + "</p><br></div><br><br><br>");
                flag = 1;
            }
            if(flag == 0)
            {
                out.println("<div style=\"width:80%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255); display:flex; justify-content: center; align-items:center;\">");
                out.print("<p>None Added!</p><br></div><br><br><br>");
            } 

            out.println("</div><div style=\"position: fixed; justify-content: space-evenly; align-items:center; top: 10vh; left: 85vw; width: 12vw; height: 90vh; display: flex; flex-direction: column;\">");
            out.println("<form action=\"http://localhost:7071/Miniproject/java/AddStocks\" method=\"get\">" +
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+ 
                    "<input type=\"submit\" value=\"ADD STOCKS\" style=\"background-color: green; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid green; border-radius: 5px; box-shadow: 2px 2px 7px green;\">"+
                "</form>");
            if(unrealised_gain >= 0)
            {
                out.println("<div style=\"font-size: 2em; color: green; text-shadow: 1px 1px 3px green;text-align: center;\">Unrealised Gain<br>" + unrealised_gain + "</div>");
            }
            else
            {
                out.println("<div style=\"font-size: 2em; color: red; text-shadow: 1px 1px 3px red;text-align: center;\">Unrealised Gain<br>" + unrealised_gain + "</div>");
            }

            //---------------------------------------------------------------------------------------------------------------------------

            Statement stmt1 = conn.createStatement();
            String status1 = request.getParameter("status"); 
            String sql1 = "SELECT * FROM realised_gain WHERE username = '" + status1 + "'";
            ResultSet rs1 = stmt1.executeQuery(sql1);

            if(rs1.next())
            {
                float realised_gain = rs1.getFloat("stock");
                out.println("<div style=\"font-size: 2em; color: rgb(80, 80, 255); text-shadow: 1px 1px 3px rgb(80, 80, 255);text-align: center;\">Realised Gain<br>" + realised_gain + "</div>");
            }
            
            //---------------------------------------------------------------------------------------------------------------------------

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();

            //---------------------------------------------------------------------------------------------------------------------------

            out.println("</div></body></html>");

            //---------------------------------------------------------------------------------------------------------------------------
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}   