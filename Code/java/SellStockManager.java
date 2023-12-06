import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class SellStockManager extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/Miniproject";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "mysql";
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
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
			String scheme = request.getParameter("scheme");
			String purchase_price = request.getParameter("purchase_price");
			String price = request.getParameter("price");
			String quantity = request.getParameter("quantity");
			String net = request.getParameter("net");
            
			String sql = "DELETE FROM purchased_stocks where username='" + username + "' AND scheme='" + scheme + "' AND purchase_price=" + purchase_price + " AND price=" + price + " AND quantity=" + quantity + " AND net=" + net;
			PreparedStatement stmt = conn.prepareStatement(sql);

            int row = stmt.executeUpdate();
            
            stmt.close();

            Statement stmt1 = conn.createStatement();
            String sql1 = "SELECT * FROM realised_gain WHERE username = '" + username + "'";
            ResultSet rs1 = stmt1.executeQuery(sql1);
            
            if(rs1.next())
            {
                float existing_gain = rs1.getFloat("stock");
                float realised_gain = (Float.parseFloat(price) * Integer.parseInt(quantity) - Float.parseFloat(purchase_price) * Integer.parseInt(quantity));
                float new_gain = existing_gain + realised_gain;
                String sql2 = "UPDATE realised_gain SET stock=" + new_gain + " WHERE username ='" + username + "'";
                PreparedStatement ps = conn.prepareStatement(sql2);
                row = ps.executeUpdate();
            }

            stmt1.close();
            rs1.close();
            conn.close();

            request.setAttribute("username", username);
            request.setAttribute("status", status);
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolioManager.jsp");
            rd.forward(request, response);

            // Get a writer pointer 
            // to display the successful result
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 