import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class DeleteMF extends HttpServlet{
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
			
			String username = request.getParameter("status");
			String scheme = request.getParameter("scheme");
			String purchase_nav = request.getParameter("purchase_nav");
			String nav = request.getParameter("nav");
			String amount = request.getParameter("amount");
			String units = request.getParameter("units");
            
			String sql = "DELETE FROM purchased_mf where username='" + username + "' AND scheme='" + scheme + "' AND purchase_nav=" + purchase_nav + " AND nav=" + nav + " AND amount=" + amount + " AND units=" + units;
			PreparedStatement stmt = conn.prepareStatement(sql);

            int row = stmt.executeUpdate();
            
            stmt.close();
            conn.close();

            request.setAttribute("status", username);
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolio.jsp");
            rd.forward(request, response);

            // Get a writer pointer 
            // to display the successful result
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 