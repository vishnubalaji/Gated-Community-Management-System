<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Maintenance Fee payment</title>
        <style>
            html, body {
                height:100%;
                width:100%;
                overflow:hidden;
                background-image: linear-gradient(45deg, #7175da, #9790F2);
            }

            .class-1 {
                position: absolute;
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 10px;
                height: 30%;
                width: 35%;
                left:35%;
                top: 40%;
                text-align: center;
                border-radius: 10px;
                box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
            }

            .btn-fee {
                border-radius: 20px;
                border: 1px solid #9790F2;
                background-color: #9790F2;
                color: #FFFFFF;
                text-decoration:none;
                font-size: 10px;
                font-weight: bold;
                padding: 12px 40px;
                letter-spacing: 1px;
                text-transform: uppercase;
                margin-top:15px;
                margin-bottom:10px;
            }

            .months-list {
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
        <div class="class-1">
            <form action="payFee" method="POST">
                
                <select class="months-list" name="months">
                    <option value="January">January</option>
                    <option value="February">February</option>
                    <option value="March">March</option>
                    <option value="April">April</option>
                    <option value="May">May</option>
                    <option value="June">June</option>
                    <option value="July">July</option>
                    <option value="August">August</option>
                    <option value="September">September</option>
                    <option value="October">October</option>
                    <option value="November">November</option>
                    <option value="December">December</option>
                </select>
                
                <button class="btn-fee" name="payFee" value="Pay 1500">Pay INR 1500</button>
            </form>
        </div>
    </body>
</html>
