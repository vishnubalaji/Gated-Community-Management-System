import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
/**
 *
 * @author Vishnu-rog
 */
public class walletFill extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        ServletContext sv = getServletContext();
        String username = (String) sv.getAttribute("username");
        sv.setAttribute("username", username);
        
        int amount = Integer.parseInt(request.getParameter("amountWallet"));
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String strDate = dateFormat.format(date);
                
        final String JDBC_Driver = "com.mysql.jdbc.Driver";

        final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";

        final String USER = "root";
        final String PASS = "root";
        out.println("<html><head><style>html, body {\n"
                + "  height:100%;\n"
                + "  width:100%;\n"
                + "  overflow:hidden;\n"
                + "  background-image: linear-gradient(45deg, #7175da, #9790F2);\n"
                + "}\n"
                + "\n"
                + ".success {\n"
                + "  position: absolute;\n"
                + "    background-color: #FFFFFF;\n"
                + "    display: flex;\n"
                + "    align-items: center;\n"
                + "    justify-content: center;\n"
                + "    flex-direction: column;\n"
                + "    padding: 0 10px;\n"
                + "    height: 25%;\n"
                + "    width: 30%;\n"
                + "    left:35%;\n"
                + "    top: 40%;\n"
                + "    text-align: center;\n"
                + "    border-radius: 10px;\n"
                + "    box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;\n"
                + "}\n"
                + "\n"
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
                + "}</style></head><body>");

        Connection conn = null;
        
//        out.println(username);
        
        try {
            Class.forName(JDBC_Driver);

            conn = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);

            Statement stmt = conn.createStatement();
            String select_currentWall = "select CurrentWallet from "+username+" order by DateOfTransaction desc limit 1;";
//            String select_currentWall = "select CurrentWallet from "+username+" order by CurrentWallet desc limit 1;";
            ResultSet r = stmt.executeQuery(select_currentWall);
            int walletWeight=0;
            
            while(r.next())
            {
                walletWeight = r.getInt("CurrentWallet");
            }
            int totalWallet = amount+walletWeight;
            String insert_wallet = "insert into " + username + " values('INR "+amount+" CR', "+totalWallet+", '" + strDate + "')";
            
            stmt.executeUpdate(insert_wallet);
            out.println("<div class=\"success\">Successfully credited to your digital wallet\n"
                    + "<a name=\"Submit\" class=\"btn-redirect\" href=\"UserHome.jsp\">Home Page</a>  \n"
                    + "</div>");
            }
        catch (Exception e) {}
        out.println("</body></html>");
            
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
