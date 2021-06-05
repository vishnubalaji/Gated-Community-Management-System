<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account deletion</title>
        
        <style>
            body {
                background-image: linear-gradient(45deg, #7175da, #9790F2);
            }

            .btn-yes, .btn-fs {
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
                cursor: pointer;
            }

            .main {  
                position: absolute;
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 20px;
                height: 45%;
                width: 20%;
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
        <div class="main">
        <form action="deleteAccountDone.jsp" method="POST">
            <b><i>Are you sure you want to delete your account?</i></b> This action cannot be <b>undone</b>.
            <button class="btn-yes">Yes</button>
            <a href="UserHome.jsp" class="btn-fs">No</a>
        </form>
        </div>
    </body>
</html>
