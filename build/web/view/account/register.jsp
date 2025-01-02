<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Register Page</title>
  </head>
  <body>
    <h1>Register Page</h1>
    <% String error = (String) request.getAttribute("error"); if (error != null)
    { %>
    <p style="color: red"><%=error%></p>
    <% } %>
    <form action="Auth" method="POST">
      <input type="hidden" name="action" value="register" />

      <label>Username: </label>
      <input type="text" name="username" required /><br /><br />

      <label>Password: </label>
      <input type="password" name="password" required /><br /><br />

      <label>Role: </label>
      <select name="role" required>
        <option value="0">Customer</option>
        <option value="1">Staff</option></select
      ><br /><br />

      <input type="submit" value="Register" />
    </form>

    <p>Already have an account? <a href="Auth">Login here</a></p>
  </body>
</html>
