import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Scanner;

import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 


/**
 *
 * @author Vishnu-rog
 */
public class UserCreat extends HttpServlet {
    
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
            throws ServletException, IOException, NumberFormatException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("passwd");            
            String password_check = request.getParameter("passwdre");
            String password_hash = null;
            
            int HouseNo = Integer.parseInt(request.getParameter("house"));
            int BlockNo = Integer.parseInt(request.getParameter("block"));
            long ContactNo = Long.parseLong(request.getParameter("contact"));
            String ContactNo_string = String.valueOf(ContactNo);
            int _length = String.valueOf(ContactNo).length();
            
            int houseno,blockno; /* For verification of redundancy */
            
            String EmailID = request.getParameter("email");
            
            final String JDBC_Driver = "com.mysql.jdbc.Driver";
            
            final String DB_URL_gcms = "jdbc:mysql://localhost:3306/gcms";
            final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";
            final String DB_URL_maintfee = "jdbc:mysql://localhost:3306/maintenancefee";
            
            final String USER = "root";
            final String PASS = "root";

            Connection conn1 = null, conn2 = null, conn3 = null;
            
            /* Compare passwords */
            if(password.compareTo(password_check)!=0 || password.isEmpty() || password_check.isEmpty()) {
                response.sendRedirect("PasswordNotTheSame.html");
                return ;
            }
            
            /* Contact No length check */
            if(String.valueOf(ContactNo).length()!= 10) {
                response.sendRedirect("ContactLengthIncorrect.html");
                return ;
            }
            
            /* Email ID regex match */
            if(!Pattern.matches("[a-zA-Z.0-9]+@[a-zA-Z0-9]+.com",EmailID)) {
                response.sendRedirect("IncorrectMailID.html");
                return ;
            }
            
            try {
                Class.forName(JDBC_Driver);
                
                conn1 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                conn2 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
                conn3 = DriverManager.getConnection(DB_URL_maintfee, USER, PASS);
                
                Statement stmt1 = conn1.createStatement();
                Statement stmt2 = conn2.createStatement();
                Statement stmt3 = conn3.createStatement();
                
                /* Check if unique details are already in the DB */
                String select = "Select HouseNo,BlockNo from userauthen where username='" + username + "' or ContactNo='"+ ContactNo_string +"' or EmailID='"+ EmailID +"';";
                ResultSet r = stmt1.executeQuery(select);
//                out.println("Yo, this aint working1");
                if (r.next()) {
//                    out.println(houseno+" "+blockno);
                    out.println("<h1><b>Redundant details are given.</b></h1><br>"
                            + "<a href=UserAuthentication.html>Click here to redirect to the sign up page</a>");
                }

                else {
                    String _select = "Select HouseNo,BlockNo from userauthen where houseno=" + HouseNo + " and BlockNo="+ BlockNo +";";
                    r = stmt1.executeQuery(_select);
                    
                    if(r.next()) {
                        out.println("<h1><b>Redundant details are given.</b></h1><br>"
                            + "<a href=UserAuthentication.html>Click here to redirect to the sign up page</a>");
                    }
                    
                    else {
                        /* Insert details into the DB */
                        try {
                             password_hash= toHexString(getSHA(password));
//                            out.println(password_hash);
                        } 
                        catch (NoSuchAlgorithmException e) {}

                        String insert = "insert into userauthen values('"+name+"', '"+username+"', '"+password_hash+"', "+HouseNo+", "+BlockNo+", '"+ContactNo_string+"', '"+EmailID+"');";
//                        String insert = "insert into userauthen values('"+name+"', '"+username+"', '"+password+"', "+HouseNo+", "+BlockNo+", "+ContactNo+", '"+EmailID+"');";
//                        out.println("Yo, this aint working2");
                        stmt1.executeUpdate(insert);
//                        out.println("Yo, this aint working2");
                        out.println("Account creation with credentials is successful.<br>");
                        
                        /* Create Wallet transaction table */
                        Date date = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
                        String strDate = dateFormat.format(date);
//                        out.println("yo");
                        String create_wallet = "create table "+username+"( TransactionHistory varchar(15) NOT NULL, CurrentWallet int NOT NULL, DateOfTransaction varchar(30) NOT NULL );";
                        String insert_wallet = "insert into "+username+" values('INR 0', 0, '"+strDate+"')";
                        
                        stmt2.executeUpdate(create_wallet);
//                        out.println("yo10");
                        stmt2.executeUpdate(insert_wallet);
//                        out.println("yo1");
                        out.println("Transaction table creation is successful.<br>");
                        
                        /* Create Maintenance fee status table */
                        String create_maintain = "create table "+username+"( January varchar(15), February varchar(15), March varchar(15), April varchar(15), May varchar(15), June varchar(15), July varchar(15), August varchar(15), September varchar(15), October varchar(15), November varchar(15), December varchar(15), Year int NOT NULL );";
                        String insert_val = "insert into "+username+"(Year) values(2021);";
                        stmt3.executeUpdate(create_maintain);
                        stmt3.executeUpdate(insert_val);
                        out.println("Maintenance fee table creation is successful.<br>You are good to go !");
                        
                        out.println("<a href=UserAuthentication.html>Click me to redirect to Login page</a>");
                    }
                }
            } 
            
            catch (Exception e) {out.println(e);}
            
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
