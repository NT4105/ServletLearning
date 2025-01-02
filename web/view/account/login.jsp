<%-- Document : login Created on : Dec 26, 2024, 10:47:50 PM Author :
vothimaihoa --%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login Page</title>
  </head>
  <body>
    <h1>Login Page</h1>
    <% String error = (String) request.getAttribute("error"); String message =
    (String) request.getAttribute("message"); if (error != null) { %>
    <p style="color: red"><%=error%></p>
    <% } if (message != null) { %>
    <p style="color: green"><%=message%></p>
    <% } %>
    <form action="Auth" method="POST">
      <label>Username: </label>
      <input type="text" name="username" required /><br /><br />

      <label>Password: </label>
      <input type="password" name="password" required /><br /><br />

      <input type="submit" value="Login" />
    </form>

    <p>
      Don't have an account? <a href="Auth?action=register">Register here</a>
    </p>
  </body>
</html>
