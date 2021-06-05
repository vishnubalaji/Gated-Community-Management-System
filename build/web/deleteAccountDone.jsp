<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <style>
            body {
                background-image: linear-gradient(45deg, #7175da, #9790F2);
            }

            .btn-redirect {
                border-radius: 20px;
                border: 1px solid #9790F2;
                background-color: #9790F2;
                color: #FFFFFF;
                font-size: 10px;
                font-weight: bold;
                padding: 12px 40px;
                letter-spacing: 1px;
                text-transform: uppercase;
                margin : 10px;
                text-decoration: none;
            }

            .delete {  
                position: absolute;
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 50px;
                height: 45%;
                width: 17%;
                left:40%;
                top: 20%;
                text-align: center;
                border-radius: 10px;
                box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
            }
        </style>
    </head>
    
    <%
        
    %>
    
    <body>
        <div class="delete">
        <%
            
            ServletContext sv = getServletContext();
            String username = (String) sv.getAttribute("username");
            sv.setAttribute("username", username);
            
            final String JDBC_Driver = "com.mysql.jdbc.Driver";
            
            final String DB_URL_gcms = "jdbc:mysql://localhost:3306/gcms";
            final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";
            final String DB_URL_maintfee = "jdbc:mysql://localhost:3306/maintenancefee";
            
            final String USER = "root";
            final String PASS = "root";

            Connection conn1 = null, conn2 = null, conn3 = null;
            
            try {
                Class.forName(JDBC_Driver);
                
                conn1 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                conn2 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
                conn3 = DriverManager.getConnection(DB_URL_maintfee, USER, PASS);
                
                Statement stmt1 = conn1.createStatement();
                Statement stmt2 = conn2.createStatement();
                Statement stmt3 = conn3.createStatement();
                
                /* Delete account from GCMS */
                
                String delete_acc="delete from userauthen where username='"+username+"';";
                stmt1.executeUpdate(delete_acc);
                
                /* Delete  maintenance fee table */
                
                String delete_maintainfee="drop table "+username+";";
                stmt2.executeUpdate(delete_maintainfee);
                
                /* Delete transaction history table */
                
                String delete_usrtrans="drop table "+username+";";
                stmt3.executeUpdate(delete_usrtrans);
                
                out.println("Account deletion successful!");
                
            } 
            
            catch (Exception e) {out.println(e);}
        %>
        <a href="UserAuthentication.html" class="btn-redirect">Login/Signup page</a>
        </div>
        
    </body>
</html>
