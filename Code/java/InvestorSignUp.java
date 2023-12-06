import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class InvestorSignUp extends HttpServlet{
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
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String sql = "SELECT * FROM investor_login WHERE email = '" + email + "' OR username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            RequestDispatcher rd = null;

            if(rs.next())
            {
                request.setAttribute("status", "exists");
                rd = request.getRequestDispatcher("jsp/SignUp.jsp");
                rd.forward(request, response);
            }
            else 
            {
                PreparedStatement st = conn.prepareStatement("insert into investor_login values(?, ?, ?, '')");
                st.setString(1, request.getParameter("email"));
                st.setString(2, request.getParameter("username"));
                st.setString(3, request.getParameter("password"));
                st.executeUpdate();
                st.close();
   
                //--------------------------------------------------------------------------------------
                PreparedStatement ps = conn.prepareStatement("insert into realised_gain values(?, 0, 0, 0)");
                ps.setString(1, request.getParameter("username"));
                ps.executeUpdate();
                ps.close();
                //--------------------------------------------------------------------------------------

                request.setAttribute("status", "success");
                rd = request.getRequestDispatcher("jsp/SignUp.jsp");
                rd.forward(request, response);
            }
  
            // Close all the connections
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 