<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
    <head>
        <title>User Home page</title>
        
        <style>
            body {
                background-image: linear-gradient(45deg, #7175da, #9790F2);
            }

            .btn-vt , .btn-am, .btn-mf, .btn-fs, .btn-lg, .btn-cp, .btn-da {
                border-radius: 20px;
                border: 1px solid #9790F2;
                background-color: #9790F2;
                color: #FFFFFF;
                font-size: 10px;
                font-weight: bold;
                padding: 12px 40px;
                letter-spacing: 1px;
                text-transform: uppercase;
                text-decoration:none;
                margin : 10px;
            }
            
            .btn-da {
                background-color: #DC143C;
            }
            
            .btn-da:hover {
                background-color: red;
            }
            
            .btn-lg:hover {
                background-color: red;
            }

            .div-1, .div-2, .div-3, .div-4 {  
                position: absolute;
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 10px;
                height: 40%;
                width: 20%;
                left:10%;
                top: 20%;
                text-align: center;
                border-radius: 10px;
                box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
            }

            .div-2 {
                height: 50%;
                width: 20%;
                left:40%;
                top: 10%;
            }

            .div-3 {
                height: 20%;
                width: 20%;
                left:10%; 
                top: 65%;
            }

            .div-4 {
                height: 35%;
                width: 20%;
                left:70%;
                top: 40%;
            }

            .usrname {
                background-color: #eee;
                border: none;
                padding: 12px 15px;
                margin-bottom: 10px;
                margin-left : 1px;
                width: 100%;
            }

            .email {
                background-color: #eee;
                border: none;
                padding: 12px 15px;
                margin-bottom: 10px;
                margin-left : 1px;
                width: 100%;
            }
        </style>
    </head>
    
    <%
        ServletContext sv = getServletContext();
        String username = (String) sv.getAttribute("username");
        sv.setAttribute("username", username);
    %>
    
    <body>
        <div class="div-1">
            <h1>Welcome ${username} !</h1>
            <a href="index.html" class="btn-lg">Logout</a>
            <a href="changePassword.jsp" class="btn-cp">Change Password</a>
            <a href="deleteAccount.jsp" class="btn-da">Delete Account</a>
        </div>
        
        <div class="div-2">
            <a href="UserTransactions?username=${username}" class="btn-vt">View Transactions</a><br>
            <a href="walletFillPage.jsp" class="btn-am">Add money to your wallet</a> &nbsp 
            <a href="payFeePage.jsp" class="btn-mf">Pay maintenance fee</a>
            <a href="UserMaintenanceFee" class="btn-fs">Your monthly maintenance fee status</a><br>
        </div>
        
        <div class="div-3">
            <h2>Your Digital Wallet Balance:</h2><h3>
            <%
                final String JDBC_Driver = "com.mysql.jdbc.Driver";
                final String DB_URL_usrtrans = "jdbc:mysql://localhost:3306/usertransactions";
                final String DB_URL_gcms = "jdbc:mysql://localhost:3306/gcms";

                final String USER = "root";
                final String PASS = "root";

                Connection conn1 = null, conn2 = null;

                int walletCurrent = 0;

                int houseno = 0, blockno = 0;
                String emailid = null;
                String contact = null;

                try {
                    Class.forName(JDBC_Driver);
                    conn1 = DriverManager.getConnection(DB_URL_usrtrans, USER, PASS);
//                    conn2 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);

                    Statement stmt1 = conn1.createStatement();
//                    Statement stmt2 = conn2.createStatement();

//                    String digiWallet = "Select CurrentWallet from " + username + " order by CurrentWallet desc limit 1;";
                    String digiWallet = "Select CurrentWallet from " + username + " order by DateOfTransaction desc limit 1;";
//                    String personalDetails = "Select HouseNo,BlockNo,ContactNo,EmailID from userauthen where username='"+username+"';";

//                    out.println("Screw you!");
                    ResultSet r1 = stmt1.executeQuery(digiWallet);
//                    ResultSet r2 = stmt2.executeQuery(personalDetails);
//                    ResultSet r1 = stmt1.executeQuery("select CurrentWallet from pytorcher;");

                    while (r1.next()) {
                        walletCurrent = r1.getInt("CurrentWallet");
//                        out.println("current wallet balance is INR "+walletCurrent);
                        out.println("INR " + walletCurrent);
                    }

//                    while(r2.next()) {
//                        houseno = r2.getInt("HouseNo");
//                        blockno = r2.getInt("BlockNo");
//                        emailid = r2.getString("EmailID");
//                        contact = r2.getInt("ContactNo");
//                    }
                } catch (Exception e) {
                }
            %></h3>
        </div>
        
        <div class="div-4">
            <h1>Personal Information</h1><br>
            <%
                try {
                    conn2 = DriverManager.getConnection(DB_URL_gcms, USER, PASS);
                    Statement stmt2 = conn2.createStatement();

                    String personalDetails = "Select HouseNo,BlockNo,ContactNo,EmailID from userauthen where username='" + username + "';";

//                    out.println("Screw you!");
                    ResultSet r2 = stmt2.executeQuery(personalDetails);

                    while (r2.next()) {
                        houseno = r2.getInt("HouseNo");
                        blockno = r2.getInt("BlockNo");
                        emailid = r2.getString("EmailID");
                        contact = r2.getString("ContactNo");
                    }

                    out.println("<b>HouseNo : </b>" + houseno + "<b>BlockNo : </b>" + blockno + "<b>Email ID : </b>" + emailid + "<b>Contact No : </b>" + contact);
                } catch (Exception e) {
                }
            %>
        </div>

    </body>
</html>
