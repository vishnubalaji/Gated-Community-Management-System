import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Vishnu-rog
 */
public class payFee extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html>\n"
                + "    <head>\n"
                + "        <title>Yaay!!</title>\n"
                + "<style>"
                + "html, body {\n"
                + "  height:100%;\n"
                + "  width:100%;\n"
                + "  overflow:hidden;\n"
                + "  background-image: linear-gradient(45deg, #7175da, #9790F2);\n"
                + "}\n"
                + "\n"
                + ".class-1 {\n"
                + "    text-transform:uppercase;\n"
                + "    position: absolute;\n"
                + "    background-color: #FFFFFF;\n"
                + "    display: flex;\n"
                + "    align-items: center;\n"
                + "    justify-content: center;\n"
                + "    flex-direction: column;\n"
                + "    padding: 0 10px;\n"
                + "    height: 30%;\n"
                + "    width: 35%;\n"
                + "    left:35%;\n"
                + "    top: 40%;\n"
                + "    text-align: center;\n"
                + "    border-radius: 10px;\n"
                + "    box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;\n"
                + "}"
                + ".btn-redirect {\n"
                + "	border-radius: 20px;\n"
                + "text-decoration:none;"
                + "	border: 1px solid #9790F2;\n"
                + "	background-color: #9790F2;\n"
                + "	color: #FFFFFF;\n"
                + "	font-size: 10px;\n"
                + "	font-weight: bold;\n"
                + "	padding: 12px 40px;\n"
                + "	letter-spacing: 1px;\n"
                + "	text-transform: uppercase;\n"
                + "  margin-top:30px;\n"
                + "  margin-bottom:10px;\n"
                + "}"
                + "</style>"
                + "    </head>\n"
                + "    \n"
                + "    <body>");
        
        String month = request.getParameter("months");
        String status="PAID";
//        exit(0);

        ServletContext sv = getServletContext();
        String username = (String) sv.getAttribute("username");
        sv.setAttribute("username", username);
        
        int fee=1500;
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String strDate = dateFormat.format(date);

        final String JDBC_Driver = "com.mysql.jdbc.Driver";

        final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";
        final String DB_URL_maintainfee = "jdbc:mysql://localhost:3306/maintenancefee";

        final String USER = "root";
        final String PASS = "root";
        Connection conn1 = null, conn2 = null;
//        out.println(username);
        
        /**
        
        Transaction history update
        
        **/

        try {
            Class.forName(JDBC_Driver);
            conn1 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
            conn2 = DriverManager.getConnection(DB_URL_maintainfee, USER, PASS);
//            out.println(month);
            Statement stmt1 = conn1.createStatement();
            Statement stmt2 = conn2.createStatement();

            String select = "select CurrentWallet from "+username+" order by DateOfTransaction desc limit 1;";
            ResultSet r = stmt1.executeQuery(select);
//            out.println(r.next());
            int finalAmount=0, digiWallet=0;
//            out.println(month+"1");
//            out.println(r1.next());

            while(r.next()) {
                digiWallet = r.getInt("CurrentWallet");
//                out.println(month+"2");
            }
            
            if(digiWallet>=1500) {                
                /**
        
                Maintenance fee update

                **/
                String select_fee = "select "+month+" from "+username+" where Year=2021";
                r = stmt2.executeQuery(select_fee);//out.println(month+"9");
                r.next();//out.println(month+"3");
//                out.println(r.getString(month));
//                out.println(status.compareTo(r.getString(month)));
                
                if(r.getString(month) == null) {
//                out.println(month+"3");
                finalAmount = digiWallet - fee;
                String insert = "insert into " + username + " values('INR 1500 DR', " + finalAmount + ", '" + strDate + "');";
                stmt1.executeUpdate(insert);
                // Success debiting
                
                String insert_fee = "update "+username+" set "+month+"='PAID' where Year=2021;";
                stmt2.executeUpdate(insert_fee);//out.println(month+"4");
                
                out.println("<div class=\"class-1\">\n"
                        + "            Success!!\n"
                        + "<a name=\"Submit\" class=\"btn-redirect\" href=\"UserHome.jsp\">Home Page</a>  \n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>"
                );
                
                }
                
                if(status.compareTo(r.getString(month))==0) {
                    out.println("<div class=\"class-1\">\n"
                        + "            Already paid!\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>");
                }
            }
            
            else {
                out.println("<div class=\"class-1\">\n"
                        + "            Insufficient balance\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>");
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
