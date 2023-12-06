import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class InsertMFManager extends HttpServlet{
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
            String sql = "SELECT * FROM purchased_mf WHERE username ='" + request.getParameter("username") + "' AND scheme = '" + request.getParameter("scheme") +
             "' AND purchase_nav = " + request.getParameter("purchase_nav") + " AND nav = " + request.getParameter("nav");
            
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
            {
                // Clean-up environment
                    
                float total_amount = rs.getFloat("amount") + Float.parseFloat(request.getParameter("amount"));
                float total_units = rs.getFloat("units") + Float.parseFloat(request.getParameter("units"));

                rs.close();
                stmt.close();

                sql = "UPDATE purchased_mf SET amount=" + total_amount + ", units = " +  total_units + " WHERE username ='" + request.getParameter("username") + "' AND scheme = '" + request.getParameter("scheme") +
                "' AND purchase_nav = " + request.getParameter("purchase_nav") + " AND nav = " + request.getParameter("nav");

                out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                
                int row = ps.executeUpdate();
            }
            
            
            else {
                PreparedStatement st = conn.prepareStatement("insert into purchased_mf values(?, ?, ?, ?, ?, ?)");
                st.setString(1, request.getParameter("username"));
                st.setString(2, request.getParameter("scheme"));
                st.setFloat(3, Float.valueOf(request.getParameter("purchase_nav")));
                st.setFloat(4, Float.valueOf(request.getParameter("nav")));
                st.setFloat(5, Float.valueOf(request.getParameter("amount")));
                st.setFloat(6, Float.valueOf(request.getParameter("units")));
                st.executeUpdate();

                st.close();
                
            }
     
            conn.close();

            request.setAttribute("status", request.getParameter("status"));
            request.setAttribute("username", request.getParameter("username"));
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolioManager.jsp");
            rd.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 