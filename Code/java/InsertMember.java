import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class InsertMember extends HttpServlet{
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
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			// Execute SQL query 

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM investor_login WHERE username ='" + request.getParameter("username") + "'";            
            ResultSet rs = stmt.executeQuery(sql);           

            if(rs.next())
            {
                // Clean-up environment

                rs.close();
                stmt.close();
                
                sql = "UPDATE investor_login SET manager='" + request.getParameter("status") + "' WHERE username ='" + request.getParameter("username") + "'";

                PreparedStatement ps = conn.prepareStatement(sql);

                int row = ps.executeUpdate();
            }
                
            conn.close();

            request.setAttribute("status", request.getParameter("status"));
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolioManager.jsp");
            rd.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 