import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Vishnu-rog
 */
public class AdminOperations extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><style>\n" +
        "table {\n" +
        "  font-family: Arial, Helvetica, sans-serif;\n" +
        "  border-collapse: collapse;\n" +
        "  width: 100%;\n" +
        "}\n" +
        "\n" +
        "td, th {\n" +
        "  border: 1px solid #ddd;\n" +
        "  padding: 8px;\n" +
        "}\n" +
        "\n" +
        "tr:nth-child(even){background-color: #f2f2f2;}\n" +
        "\n" +
        "tr:hover {background-color: #ddd;}\n" +
        "\n" +
        "th {\n" +
        "  padding-top: 12px;\n" +
        "  padding-bottom: 12px;\n" +
        "  text-align: left;\n" +
        "  background-color: #F89880;\n" +
        "  color: white;\n" +
        "}\n" +
        "</style></head>");
        
        
        String buttonStatus = request.getParameter("Submit");
        String username = request.getParameter("username");
        
//        out.println(buttonStatus);
        
        final String JDBC_Driver="com.mysql.jdbc.Driver";
        
        final String DB_URL_gcms="jdbc:mysql://localhost:3306/gcms";
        final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";
        final String DB_URL_maintainfee = "jdbc:mysql://localhost:3306/maintenancefee";
        
        final String USER="root";
        final String PASS="root";
        Connection conn1 = null, conn2 = null, conn3 = null, conn4 = null;

        /**** 
        
        View user details 
        
        ****/
        
        if( buttonStatus.compareTo("userDetails") == 0 ) {
            try {
                Class.forName(JDBC_Driver);
                conn1 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                Statement stmt1 = conn1.createStatement();

                String select1 = "Select * from userauthen where username='" + username + "';";
//                out.println(username);
                ResultSet r1 = stmt1.executeQuery(select1);

                out.println("<body><table><tr><th>Name</th>"
                        + "<th>Username</th>"
                        //+ "<th>Password</th>"
                        + "<th>HouseNo</th>"
                        + "<th>BlockNo</th>"
                        + "<th>ContactNo</th>"
                        + "<th>EmailID</th></tr>");

                if(r1.next()) {
                    out.println("<tr>"
                            + "<td>"+r1.getString("name")+"</td>"
                            + "<td>"+r1.getString("username")+"</td>"
                            //+ "<td>"+r1.getString("password")+"</td>"
                            + "<td>"+r1.getInt("HouseNo")+"</td>"
                            + "<td>"+r1.getInt("BlockNo")+"</td>"
                            + "<td>"+r1.getString("ContactNo")+"</td>"
                            + "<td>"+r1.getString("EmailID")+"</td>"
                            + "</tr>");
                }
                else {
                out.println("<tr><td colspan=7 align=center> Data unavailable </td></tr>");
                }
            }
            catch(Exception e) {out.println(e);}
            out.println("</body></table></html>");
        }
        
        /**** 
        
        View transaction history 
        
        ****/
        
        if( buttonStatus.compareTo("transactionHistory") == 0 ) {
            try {
                Class.forName(JDBC_Driver);
                conn2 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
                Statement stmt2 = conn2.createStatement();

                String select = "Select * from " + username + " order by DateOfTransaction;";
                ResultSet r2 = stmt2.executeQuery(select);
                
                out.println("<body><table><tr><th>TransactionHistory</th>"
                        + "<th>CurrentWallet</th>"
                        + "<th>DateOfTransaction</th></tr>");
                while(r2.next()) {
                        out.println("<tr>"
                                + "<td>"+r2.getString("TransactionHistory")+"</td>"
                                + "<td>"+r2.getInt("CurrentWallet")+"</td>"
                                + "<td>"+r2.getString("DateOfTransaction")+"</td>"
                                + "</tr>");
                }

            }
            catch(Exception e) {}
            out.println("</body></table></html>");
        }
        
        /**** 
        
        View all user details 
        
        ****/
        
        if( buttonStatus.compareTo("allUser") == 0 ) {
            try {
                Class.forName(JDBC_Driver);
                conn3 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                Statement stmt3 = conn3.createStatement();

                String select = "Select * from userauthen;";
                ResultSet r3 = stmt3.executeQuery(select);

                out.println("<body><table><tr><th>Name</th>"
                        + "<th>Username</th>"
                        //+ "<th>Password</th>"
                        + "<th>HouseNo</th>"
                        + "<th>BlockNo</th>"
                        + "<th>ContactNo</th>"
                        + "<th>EmailID</th></tr>");

                while(r3.next()) {
                    out.println("<tr>"
                            + "<td>"+r3.getString("name")+"</td>"
                            + "<td>"+r3.getString("username")+"</td>"
                            //+ "<td>"+r3.getString("password")+"</td>"
                            + "<td>"+r3.getInt("HouseNo")+"</td>"
                            + "<td>"+r3.getInt("BlockNo")+"</td>"
                            + "<td>"+r3.getString("ContactNo")+"</td>"
                            + "<td>"+r3.getString("EmailID")+"</td>"
                            + "</tr>");
                }
            }
            catch(Exception e) {}
            out.println("</body></table></html>");
        }
        
        /**** 
        
        Maintenance fee status
        
        ****/
        
        if( buttonStatus.compareTo("feeStatus") == 0 ) {
//            out.println(buttonStatus);
            try {
                Class.forName(JDBC_Driver);
                conn4 = DriverManager.getConnection(DB_URL_maintainfee, USER, PASS);
                Statement stmt4 = conn4.createStatement();

                String select = "Select * from "+username+";";
                ResultSet r4 = stmt4.executeQuery(select);
//                out.println(buttonStatus);

                out.println("<body><table><tr><th>Year</th>"
                        + "<th>January</th>"
                        + "<th>February</th>"
                        + "<th>March</th>"
                        + "<th>April</th>"
                        + "<th>May</th>"
                        + "<th>June</th>"
                        + "<th>July</th>"
                        + "<th>August</th>"
                        + "<th>September</th>"
                        + "<th>October</th>"
                        + "<th>November</th>"
                        + "<th>December</th></tr>");
                while (r4.next()) {
                    out.println("<tr>"
                            + "<td>" + r4.getInt("Year") + "</td>"
                            + "<td>" + r4.getString("January") + "</td>"
                            + "<td>" + r4.getString("February") + "</td>"
                            + "<td>" + r4.getString("March") + "</td>"
                            + "<td>" + r4.getString("April") + "</td>"
                            + "<td>" + r4.getString("May") + "</td>"
                            + "<td>" + r4.getString("June") + "</td>"
                            + "<td>" + r4.getString("July") + "</td>"
                            + "<td>" + r4.getString("August") + "</td>"
                            + "<td>" + r4.getString("September") + "</td>"
                            + "<td>" + r4.getString("October") + "</td>"
                            + "<td>" + r4.getString("November") + "</td>"
                            + "<td>" + r4.getString("December") + "</td>"
                            + "</tr>");
                }
            }
            catch(Exception e) {}
            out.println("</body></table></html>");
        }
        
        /*
        
        List of unpaid maintenancefee users
        
        */
        
        if( buttonStatus.compareTo("Unpaid") == 0 ) {
            String month = request.getParameter("months");
            String foo = null;
            try {
                Class.forName(JDBC_Driver);
                
                conn3 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                Statement stmt3 = conn3.createStatement();
                
                conn4 = DriverManager.getConnection(DB_URL_maintainfee, USER, PASS);
                Statement stmt4 = conn4.createStatement();

                String select_user = "Select username from userauthen;";
                
                ResultSet r3 = stmt3.executeQuery(select_user);

                out.println("<body><table><tr><th>Username</th></tr>");
//                        + "<th>Username</th>"
//                        + "<th>Password</th>"
//                        + "<th>HouseNo</th>"
//                        + "<th>BlockNo</th>"
//                        + "<th>ContactNo</th>"
//                        + "<th>EmailID</th></tr>");

                while(r3.next()) {
                    String user = r3.getString("username");
                    String select_monthStatus = "Select "+month+" from "+user+" where "+month+"='PAID';";
                    ResultSet r4 = stmt4.executeQuery(select_monthStatus);
                    
                    if(r4.next()) {                        
//                        out.println(r4.getString(month));
                        if(r4.getString(month).compareTo("PAID")==0) {
//                            out.println(user);
//                            out.println("<tr>"
//                            + "<td>"+user+"</td>"
//                            + "</tr>");
                        }
                    }
                    
                    else {
                        out.println("<tr>"
                            + "<td>"+user+"</td>"
                            + "</tr>");
                    }
                }
            }
            catch(Exception e) {}
            out.println("</body></table></html>");
        }
        
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
