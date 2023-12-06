import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Chat extends HttpServlet{
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
            String username = request.getParameter("status");
            String sql = "SELECT * FROM investor_login WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            RequestDispatcher rd = null;

            if(rs.next())
            {
                if(!rs.getString("manager").equals(""))
                {
                    PreparedStatement st = conn.prepareStatement("insert into chat values(?, ?, ?)");
                    st.setString(1, request.getParameter("status"));
                    st.setString(2, rs.getString("manager"));
                    st.setString(3, "Requested");
                    st.executeUpdate();
                    st.close();
                }
            }
            
            request.setAttribute("status", request.getParameter("status"));
            rd = request.getRequestDispatcher("jsp/ViewPortfolio.jsp");
            rd.forward(request, response);
  
            // Close all the connections
            conn.close();
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 