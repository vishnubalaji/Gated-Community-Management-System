import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

/**
 *
 * @author Vishnu-rog
 */
public class UserAuth extends HttpServlet {
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    { 
        // Static getInstance method is called with hashing SHA 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
        // digest() method called 
        // to calculate message digest of an input 
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    }
    
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash); 
  
        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        // Pad with leading zeros
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        final String JDBC_Driver="com.mysql.jdbc.Driver";
        final String DB_URL="jdbc:mysql://localhost:3306/gcms";
        final String USER="root";
        final String PASS="root";
        
        String username = request.getParameter("username");
        String password = request.getParameter("passwd");;
        String password_hash = null;
            
        Connection conn = null;
        try {
            Class.forName(JDBC_Driver);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            
            try {
                password_hash = toHexString(getSHA(password));
//                            out.println(password_hash);
            } catch (NoSuchAlgorithmException e) {
            }
//            out.println("Connection Established<br>");
            
//            String query = "Select * from userauthen where username='" + username + "' and password='" + password + "'";
            String select = "Select * from userauthen where username='" + username + "' and password='" + password_hash + "';";
//            String select = "Select exists( Select * from userauthen where username='" + username + "' and password='" + password + "');";
            
            ResultSet r = stmt.executeQuery(select);
//            out.println(r.next());  // r.next() returns True if exists and False if it doesn't
            
            if(r.next()) {
                ServletContext sv = getServletContext();
                sv.setAttribute("username", username);
                response.sendRedirect("UserHome.jsp");
            }
            
            else {
                out.println("Incorrect credentials :("
                        + "<br><a href=UserAuthentication.html>Go back to the Login page</a>");
            }
        }    
        catch(Exception e) {}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

