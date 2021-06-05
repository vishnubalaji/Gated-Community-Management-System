<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Wallet famished?</title>
    </head>
    <%
        ServletContext sv = getServletContext();
        String username = (String) sv.getAttribute("username");
        sv.setAttribute("username", username);
    %>
    <style>
        body {
            background-image: linear-gradient(45deg, #7175da, #9790F2);
        }

        .btn-aw {
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

        .card-1 {  
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

        .wallet {
            background-color: #eee;
            border: none;
            padding: 12px 15px;
            margin-bottom: 10px;
            margin-left : 1px;
            width: 100%;
        }
    </style>
    
    <body>
      
      <div class="card-1">
        
        <form action="walletFill" method="POST">
            <input type="number" class="wallet" name="amountWallet" placeholder="Enter the amount" required>
            <button name="Submit" class="btn-aw" value="amountCredit">Add money</button>
        </form>
        
       </div>
      
    </body>
</html>
