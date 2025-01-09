<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Register Page</title>
  </head>
  <body>
    <h1>Register Page</h1>

    <c:if test="${not empty error}">
      <div style="color: red">${error}</div>
    </c:if>

    <c:if test="${not empty success}">
      <div style="color: green">${success}</div>
    </c:if>

    <form action="Auth" method="POST">
      <input type="hidden" name="action" value="register" />

      <label>Username: </label>
      <input type="text" name="username" required /><br /><br />

      <label>Password: </label>
      <input type="password" name="password" required /><br /><br />

      <input type="hidden" name="role" value="0" />

      <input type="submit" value="Register" />
    </form>

    <p>Already have an account? <a href="Auth">Login here</a></p>
  </body>
</html>
