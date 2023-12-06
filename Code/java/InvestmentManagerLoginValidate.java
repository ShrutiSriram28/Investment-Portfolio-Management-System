import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InvestmentManagerLoginValidate extends HttpServlet {
		// JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        static final String DB_URL="jdbc:mysql://localhost:3306/Miniproject";
  
        //  Database credentials
        static final String USER = "root";
        static final String PASS = "mysql";

        public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        
        String email = request.getParameter("email_manager");
        String password_val = request.getParameter("password_manager");
        String correct_password = "";
        String username = "";
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM investment_manager_login WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                correct_password = rs.getString("password");
            }

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            sql = "SELECT username FROM investment_manager_login WHERE email = '" + email + "'";
            rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                username = rs.getString("username");
            }

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(((String)password_val).equalsIgnoreCase((String)correct_password))
        {
		
			// Getting RequestDispatcher object
            // for collaborating with Welcome servlet
            request.setAttribute("status", username);
            RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolioManager.jsp");
			// forwarding the request to Welcome.java
            rd.forward(request, response);
        }
		else
		{
            request.setAttribute("status", "incorrect");
            RequestDispatcher rd = request.getRequestDispatcher("jsp/Login.jsp");
            rd.forward(request, response); 
		}
    }
}