import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class AddStockClass extends HttpServlet{
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

            PreparedStatement st = conn.prepareStatement("insert into investor_login values(?, ?, ?, ?, ?, ?)");
            st.setString(1, request.getParameter("status"));
            st.setString(2, request.getParameter("scheme"));
            st.setFloat(3, Float.valueOf(request.getParameter("purchase_price")));
            st.setFloat(4, Float.valueOf(request.getParameter("price")));
            st.setInt(5, Integer.valueOf(request.getParameter("quantity")));
            st.setFloat(6, Float.valueOf(request.getParameter("net")));
            st.executeUpdate();
            st.close();
     
            // Close all the connections
            conn.close();

            request.setAttribute("status", request.getParameter("status"));
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolio.jsp");
            rd.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 