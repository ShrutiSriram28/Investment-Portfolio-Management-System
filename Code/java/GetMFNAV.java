import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class GetMFNAV extends HttpServlet {  
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
            
        String scheme_name = request.getParameter("scheme");  
    
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql = "SELECT nav FROM mf WHERE scheme='" + scheme_name + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                out.print("<input type='hidden' id='hidden_nav' value = " + rs.getString("nav") + ">");
            }
            
            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}   