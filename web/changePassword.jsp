<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change your Password</title>
        
        <style>
            body {
                background-image: linear-gradient(45deg, #7175da, #9790F2);
            }

            .btn-ch {
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
            }

            .div-1 {  
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

            .old, .new, .retype {
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
            <form action="changePassword" method="POST">
                <input type="password" placeholder="Old password" name="old" class="old" required/>
                <input type="password" placeholder="New password" name="new" class="new" required/>
                <input type="password" placeholder="Re-type password" name="retype" class="retype" required/>
                <button class="btn-ch">Change</button>
            </form>
        </div>
    </body>
</html>
