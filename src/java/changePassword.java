import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

/**
 *
 * @author Vishnu-rog
 */
public class changePassword extends HttpServlet {
    
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
        
        out.println("<html><head>");
        out.println("<style>"
                + "body {\n"
                + "    background-image: linear-gradient(45deg, #7175da, #9790F2);\n"
                + "}"
                + ".div-1 {  \n"
                + "    position: absolute;\n"
                + "    background-color: #FFFFFF;\n"
                + "    display: flex;\n"
                + "    align-items: center;\n"
                + "    justify-content: center;\n"
                + "    flex-direction: column;\n"
                + "    padding: 0 50px;\n"
                + "    height: 45%;\n"
                + "    width: 17%;\n"
                + "    left:40%;\n"
                + "    top: 20%;\n"
                + "    text-align: center;\n"
                + "    border-radius: 10px;\n"
                + "    box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;\n"
                + "}"
                + ".btn-redirect {\n"
                + "                border-radius: 20px;\n"
                + "                border: 1px solid #9790F2;\n"
                + "                background-color: #9790F2;\n"
                + "                color: #FFFFFF;\n"
                + "                font-size: 10px;\n"
                + "                font-weight: bold;\n"
                + "                padding: 12px 40px;\n"
                + "                letter-spacing: 1px;\n"
                + "                text-transform: uppercase;\n"
                + "                margin : 10px;\n"
                + "text-decoration:none;"
                + "            }"
                + "</style></head><body>");
                
        String old_password = request.getParameter("old");
        String new_password = request.getParameter("new");
        String re_password = request.getParameter("retype");
        String new_password_hash = null;
        String old_password_hash = null;
        
        try {
                             old_password_hash= toHexString(getSHA(old_password));
//                            out.println(password_hash);
                        } 
                        catch (NoSuchAlgorithmException e) {}
        
        ServletContext sv = getServletContext();
        String username = (String) sv.getAttribute("username");
        sv.setAttribute("username", username);
        
        final String JDBC_Driver = "com.mysql.jdbc.Driver";

        final String DB_URL_gcms = "jdbc:mysql://localhost:3306/gcms";

        final String USER = "root";
        final String PASS = "root";
        
        Connection conn = null;

//        out.println(username);
        try {
            Class.forName(JDBC_Driver);

            conn = DriverManager.getConnection(DB_URL_gcms, USER, PASS);

            Statement stmt = conn.createStatement();
            String select_pass = "select password from userauthen where username='"+username+"' and password='"+old_password_hash+"';";
            
            ResultSet r = stmt.executeQuery(select_pass);

            if(r.next()) {
                if(new_password.compareTo(re_password)!=0)
                {
//                    out.println("Kindly re-type the password correctly");
                    out.println("<div class=\"div-1\">Kindly re-type the password correctly</div></body></html>");
                }
                
                else {
                    try {
                             new_password_hash= toHexString(getSHA(new_password));
//                            out.println(password_hash);
                        } 
                        catch (NoSuchAlgorithmException e) {}
                    
                    String update_pass = "update userauthen set password='"+new_password_hash+"' where username='"+username+"';";
                    stmt.executeUpdate(update_pass);
                    out.println("<div class=\"div-1\">Success"
                            + "<a href=UserAuthentication.html class=btn-redirect>Login Page</a>"
                            + "</div></body></html>");
                }
            }
            
            else {
//                out.println("Incorrect old password");
                out.println("<div class=\"div-1\">Incorrect old password</div></body></html>");
            }
        } 
        
        catch (Exception e) {}
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
