import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
  
public class CancelRedirectUsernameManager extends HttpServlet {  
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
        String status = request.getParameter("status");
        request.setAttribute("status", status); 
        RequestDispatcher rd = request.getRequestDispatcher("jsp/ViewPortfolioManager.jsp");
        rd.forward(request, response);
    }
}   