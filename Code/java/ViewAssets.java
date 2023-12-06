import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class ViewAssets extends HttpServlet {  
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
            String status = request.getParameter("status"); 
            String username = request.getParameter("username"); 

            Statement stmt1 = conn.createStatement();
            String sql1 = "SELECT * FROM purchased_stocks WHERE username = '" + username + "'";
            ResultSet rs1 = stmt1.executeQuery(sql1);

            Statement stmt2 = conn.createStatement();
            String sql2 = "SELECT * FROM purchased_mf WHERE username = '" + username + "'";
            ResultSet rs2 = stmt2.executeQuery(sql2);

            Statement stmt3 = conn.createStatement();
            String sql3 = "SELECT * FROM purchased_bullion WHERE username = '" + username + "'";
            ResultSet rs3 = stmt3.executeQuery(sql3);

            String sql = "DELETE FROM chat where username='" + username + "' AND manager='" + status + "'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int row = stmt.executeUpdate();
            stmt.close();

            out.println("<!DOCTYPE html>"+
            "<html lang=\"en\">"+
            "<head>"+
                "<meta charset=\"UTF-8\">"+
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">"+
                "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>"+
                "<link href=\"https://fonts.googleapis.com/css2?family=Tomorrow&display=swap\" rel=\"stylesheet\">"+
                "<title>Display Assets</title>"+
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

                        "<form id=\"group_form\" action=\"http://localhost:7071/Miniproject/ViewGroup\" method=\"get\" style=\"display:flex; flex-direction: row; justify-content:space-evenly; align-items: center; width: 100%;\">"+
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
                "<p style=\"color:rgb(80, 80, 255); font-size: 3em; background-color: white; position:fixed; top:10vh; width: 70%;text-align: center;\"><b>PURCHASED ASSETS OF " + username + "</b></p><br><br><br><br><br>");

            //---------------------------------------------------------------------------------------------------------------------------
            int flag1 = 0;
            float unrealised_gain_stocks = 0;
            int count1 = 0;
            while(rs1.next())
            {
                count1 += 1;
                out.println("<div style=\"width:80%; height: 40vh; border-left: 10px solid red; border-radius: 1.5%; box-shadow: 2px 2px 15px red;\">");
                
                int quantity = Integer.parseInt(rs1.getString("quantity"));
                float purchase_price = Float.parseFloat(rs1.getString("purchase_price"));
                float price = Float.parseFloat(rs1.getString("price"));
                float gain = (price * quantity - purchase_price * quantity);

                unrealised_gain_stocks += (price * quantity - purchase_price * quantity);
                
                out.print("<p>STOCK</p><br>");
                out.print("<p style=\"display:inline;\">Scheme: " + rs1.getString("scheme") + "</p><p style=\"display:inline;float:right; margin-right: 2vw;\">Gain: " + gain + "</p><br><br>");
                out.print("<p>Purchase Price: " + rs1.getString("purchase_price") + "</p><br>");
                out.print("<p style=\"display: inline\">Current Price: " + rs1.getString("price") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/SellStockManager\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status" + count1 + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"username" + count1 + "\" name=\"username\" value="+username+">"+
                    "<input type=\"hidden\" id=\"scheme_hidden" + count1 + "\" name=\"scheme\" value="+rs1.getString("scheme")+">"+
                    "<input type=\"hidden\" id=\"purchase_price_hidden" + count1 + "\" name=\"purchase_price\" value="+ rs1.getString("purchase_price") +">"+
                    "<input type=\"hidden\" id=\"price_hidden" + count1 + "\" name=\"price\" value="+ rs1.getString("price") +">"+
                    "<input type=\"hidden\" id=\"quantity_hidden" + count1 + "\" name=\"quantity\" value="+ rs1.getString("quantity") +">"+
                    "<input type=\"hidden\" id=\"net_hidden" + count1 + "\" name=\"net\" value="+ rs1.getString("net") +">"+
                    "<input type=\"submit\" id=\"sell" + count1 + "\" value=\"Sell\" style=\"background-color: rgb(180, 30, 30); color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid rgb(180, 30, 30); border-radius: 5px; box-shadow: 2px 2px 7px rgb(180, 30, 30);\">"+ 
                "</form><br>");
                out.print("<p>Quantity: " + rs1.getString("quantity") + "</p><br>");
                out.print("<p>Net Amount: " + rs1.getString("net") + "</p><br></div><br><br><br>");
                flag1 = 1;
            }
            if(flag1 == 0)
            {
                out.println("<div style=\"width:80%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255); display:flex; justify-content: center; align-items:center;\">");
                out.print("<p>None Added!</p><br></div><br><br><br>");
            } 

            //---------------------------------------------------------------------------------------------------------------------------

            int flag2 = 0;
            float unrealised_gain_mf = 0;
            int count2 = 0;
            while(rs2.next())
            {
                count2 += 1;
                out.println("<div style=\"width:80%; height: 40vh; border-left: 10px solid orange; border-radius: 1.5%; box-shadow: 2px 2px 15px orange;\">");
                
                float units = Float.parseFloat(rs2.getString("units"));
                float purchase_nav = Float.parseFloat(rs2.getString("purchase_nav"));
                float nav = Float.parseFloat(rs2.getString("nav"));
                float gain = (nav * units - purchase_nav * units);

                unrealised_gain_mf += (nav * units - purchase_nav * units);
                
                out.print("<p>MUTUAL FUND</p><br>");
                out.print("<p style=\"display:inline;\">Scheme: " + rs2.getString("scheme") + "</p><p style=\"display:inline;float:right; margin-right: 2vw;\">Gain: " + gain + "</p><br><br>");
                out.print("<p>Purchase nav: " + rs2.getString("purchase_nav") + "</p><br>");
                out.print("<p style=\"display: inline\">Current nav: " + rs2.getString("nav") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/SellMFManager\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status" + count2 + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"username" + count2 + "\" name=\"username\" value="+username+">"+
                    "<input type=\"hidden\" id=\"scheme_hidden" + count2 + "\" name=\"scheme\" value="+rs2.getString("scheme")+">"+
                    "<input type=\"hidden\" id=\"purchase_nav_hidden" + count2 + "\" name=\"purchase_nav\" value="+ rs2.getString("purchase_nav") +">"+
                    "<input type=\"hidden\" id=\"nav_hidden" + count2 + "\" name=\"nav\" value="+ rs2.getString("nav") +">"+
                    "<input type=\"hidden\" id=\"amount_hidden" + count2 + "\" name=\"amount\" value="+ rs2.getString("amount") +">"+
                    "<input type=\"hidden\" id=\"units_hidden" + count2 + "\" name=\"units\" value="+ rs2.getString("units") +">"+
                    "<input type=\"submit\" id=\"sell" + count2 + "\" value=\"Sell\" style=\"background-color: rgb(180, 30, 30); color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid rgb(180, 30, 30); border-radius: 5px; box-shadow: 2px 2px 7px rgb(180, 30, 30);\">"+ 
                "</form><br>");
                out.print("<p>amount: " + rs2.getString("amount") + "</p><br>");
                out.print("<p>Units: " + rs2.getString("units") + "</p><br></div><br><br><br>");
                flag2 = 1;
            }
            if(flag2 == 0)
            {
                out.println("<div style=\"width:80%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255); display:flex; justify-content: center; align-items:center;\">");
                out.print("<p>None Added!</p><br></div><br><br><br>");
            } 

            //---------------------------------------------------------------------------------------------------------------------------

            int flag3 = 0;
            float unrealised_gain_bullion = 0;
            int count3 = 0;
            while(rs3.next())
            {
                count3 += 1;
                out.println("<div style=\"width:80%; height: 40vh; border-left: 10px solid green; border-radius: 1.5%; box-shadow: 2px 2px 15px green;\">");
                
                int quantity = Integer.parseInt(rs3.getString("quantity"));
                float purchase_price = Float.parseFloat(rs3.getString("purchase_price"));
                float price = Float.parseFloat(rs3.getString("price"));
                float gain = (price * quantity - purchase_price * quantity);

                unrealised_gain_bullion += (price * quantity - purchase_price * quantity);
                
                out.print("<p>BULLION</p><br>");
                out.print("<p style=\"display:inline;\">Scheme: " + rs3.getString("scheme") + "</p><p style=\"display:inline;float:right; margin-right: 2vw;\">Gain: " + gain + "</p><br><br>");
                out.print("<p>Purchase Price: " + rs3.getString("purchase_price") + "</p><br>");
                out.print("<p style=\"display: inline\">Current Price: " + rs3.getString("price") + "</p><br>");
                out.print("<form action=\"http://localhost:7071/Miniproject/java/SellBullionManager\" style=\"display: inline; float: right;\">"+
                    "<input type=\"hidden\" id=\"status" + count3 + "\" name=\"status\" value="+status+">"+
                    "<input type=\"hidden\" id=\"username" + count2 + "\" name=\"username\" value="+username+">"+
                    "<input type=\"hidden\" id=\"scheme_hidden" + count3 + "\" name=\"scheme\" value="+rs3.getString("scheme")+">"+
                    "<input type=\"hidden\" id=\"purchase_price_hidden" + count3 + "\" name=\"purchase_price\" value="+ rs3.getString("purchase_price") +">"+
                    "<input type=\"hidden\" id=\"price_hidden" + count3 + "\" name=\"price\" value="+ rs3.getString("price") +">"+
                    "<input type=\"hidden\" id=\"quantity_hidden" + count3 + "\" name=\"quantity\" value="+ rs3.getString("quantity") +">"+
                    "<input type=\"hidden\" id=\"net_hidden" + count3 + "\" name=\"net\" value="+ rs3.getString("net") +">"+
                    "<input type=\"submit\" id=\"sell" + count3 + "\" value=\"Sell\" style=\"background-color: rgb(180, 30, 30); color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid rgb(180, 30, 30); border-radius: 5px; box-shadow: 2px 2px 7px rgb(180, 30, 30);\">"+ 
                "</form><br>");
                out.print("<p>Quantity: " + rs3.getString("quantity") + "</p><br>");
                out.print("<p>Net Amount: " + rs3.getString("net") + "</p><br></div><br><br><br>");
                flag3 = 1;
            }
            if(flag3 == 0)
            {
                out.println("<div style=\"width:80%; height: 20vh; border-left: 10px solid rgb(80, 80, 255); border-radius: 1.5%; box-shadow: 2px 2px 15px rgb(80, 80, 255); display:flex; justify-content: center; align-items:center;\">");
                out.print("<p>None Added!</p><br></div><br><br><br>");
            } 

            //---------------------------------------------------------------------------------------------------------------------------
            
            out.print("</div>");

            //---------------------------------------------------------------------------------------------------------------------------
            out.println("<div style=\"position: fixed; justify-content: space-evenly; align-items:center; top: 20vh; left: 85vw; width: 12vw; height: 80vh; display: flex; flex-direction: column;\">");
            out.println("<form action=\"http://localhost:7071/Miniproject/java/AddStocksManager\" method=\"get\">" +
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+ 
                    "<input type=\"hidden\" id=\"username\" name=\"username\" value='" + username + "'>"+ 
                    "<input type=\"submit\" value=\"ADD STOCKS\" style=\"background-color: green; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid green; border-radius: 5px; box-shadow: 2px 2px 7px green;\">"+
                "</form>");
            if(unrealised_gain_stocks >= 0)
            {
                out.println("<div style=\"font-size: 1em; color: green; text-shadow: 1px 1px 3px green;text-align: center;\">Unrealised Gain Stocks<br>" + unrealised_gain_stocks + "</div>");
            }
            else
            {
                out.println("<div style=\"font-size: 1em; color: red; text-shadow: 1px 1px 3px red;text-align: center;\">Unrealised Gain Stocks<br>" + unrealised_gain_stocks + "</div>");
            }

            out.println("<form action=\"http://localhost:7071/Miniproject/java/AddMFManager\" method=\"get\">" +
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+ 
                    "<input type=\"hidden\" id=\"username\" name=\"username\" value='" + username + "'>"+ 
                    "<input type=\"submit\" value=\"ADD MF\" style=\"background-color: green; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid green; border-radius: 5px; box-shadow: 2px 2px 7px green;\">"+
                "</form>");
            if(unrealised_gain_mf >= 0)
            {
                out.println("<div style=\"font-size: 1em; color: green; text-shadow: 1px 1px 3px green;text-align: center;\">Unrealised Gain Mutual Funds<br>" + unrealised_gain_mf + "</div>");
            }
            else
            {
                out.println("<div style=\"font-size: 1em; color: red; text-shadow: 1px 1px 3px red;text-align: center;\">Unrealised Gain Mutual Funds<br>" + unrealised_gain_mf + "</div>");
            }
            
            out.println("<form action=\"http://localhost:7071/Miniproject/java/AddBullionManager\" method=\"get\">" +
                    "<input type=\"hidden\" id=\"status\" name=\"status\" value='" + status + "'>"+ 
                    "<input type=\"hidden\" id=\"username\" name=\"username\" value='" + username + "'>"+ 
                    "<input type=\"submit\" value=\"ADD BULLION\" style=\"background-color: green; color: white; margin-right: 2vw; width:8vw; height:6vh; border: 2px solid green; border-radius: 5px; box-shadow: 2px 2px 7px green;\">"+
                "</form>");
            if(unrealised_gain_bullion >= 0)
            {
                out.println("<div style=\"font-size: 1em; color: green; text-shadow: 1px 1px 3px green;text-align: center;\">Unrealised Gain Bullion<br>" + unrealised_gain_bullion + "</div>");
            }
            else
            {
                out.println("<div style=\"font-size: 1em; color: red; text-shadow: 1px 1px 3px red;text-align: center;\">Unrealised Gain Bullion<br>" + unrealised_gain_bullion + "</div>");
            }

            //---------------------------------------------------------------------------------------------------------------------------

            // Clean-up environment
            rs1.close();
            stmt1.close();
            rs2.close();
            stmt2.close();
            rs3.close();
            stmt3.close();
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