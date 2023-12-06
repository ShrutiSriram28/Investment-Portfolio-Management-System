import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class InvestmentManagerSignUp extends HttpServlet{
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
            String email = request.getParameter("email_manager");
            String username = request.getParameter("username_manager");
            String sql = "SELECT * FROM investment_manager_login where email = '" + email + "' or username = '" + username + "'";
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
                PreparedStatement st = conn.prepareStatement("insert into investment_manager_login values(?, ?, ?)");
                st.setString(1, request.getParameter("email_manager"));
                st.setString(2, request.getParameter("username_manager"));
                st.setString(3, request.getParameter("password_manager"));
                st.executeUpdate();
                st.close();

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