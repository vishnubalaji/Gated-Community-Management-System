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
public class UserTransactions extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        
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
        
//        out.println(username);

        final String JDBC_Driver = "com.mysql.jdbc.Driver";
        final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";

        final String USER = "root";
        final String PASS = "root";

        Connection conn1 = null;

        try {
            Class.forName(JDBC_Driver);
            conn1 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
            Statement stmt = conn1.createStatement();

            String select = "Select * from " + username + " order by DateOfTransaction;";
            ResultSet r = stmt.executeQuery(select);

            out.println("<body><table><tr><th>TransactionHistory</th>"
                    + "<th>CurrentWallet</th>"
                    + "<th>DateOfTransaction</th></tr>");
            while (r.next()) {
                out.println("<tr>"
                        + "<td>" + r.getString("TransactionHistory") + "</td>"
                        + "<td>" + r.getInt("CurrentWallet") + "</td>"
                        + "<td>" + r.getString("DateOfTransaction") + "</td>"
                        + "</tr>");
            }

        } catch (Exception e) {
        }
        out.println("</body></table></html>");
            
            
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
