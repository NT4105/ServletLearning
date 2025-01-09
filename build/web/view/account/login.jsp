<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login Page</title>
  </head>
  <body>
    <h1>Login</h1>
    <form action="Auth" method="POST">
      <input type="hidden" name="action" value="login" />

      <label>Username: </label>
      <input type="text" name="username" required /><br /><br />

      <label>Password: </label>
      <input type="password" name="password" required /><br /><br />

      <input type="submit" value="Login" />

      <c:if test="${not empty error}">
        <div style="color: red">${error}</div>
      </c:if>

      <c:if test="${not empty success}">
        <div style="color: green">${success}</div>
      </c:if>
    </form>
    <p>
      Don't have an account? <a href="Auth?action=register">Register here</a>
    </p>
  </body>
</html>
