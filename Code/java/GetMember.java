import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class GetMember extends HttpServlet {  
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
            
        String username = request.getParameter("username");  
    
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM investor_login WHERE username='" + username + "' AND manager=''";
            ResultSet rs = stmt.executeQuery(sql);

            int flag = 0;
            if(rs.next())
            {
                flag=1;
                out.println("");
            }

            if(flag == 0)
            {
                out.println("Investor not available!");
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