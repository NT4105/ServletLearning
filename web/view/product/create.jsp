<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Create Product</title>
  </head>
  <body>
    <h1>Add New Product</h1>

    <c:if test="${not empty error}">
      <p style="color: red">${error}</p>
    </c:if>

    <form action="Product" method="POST">
      <label for="name">Product Name:</label>
      <input type="text" id="name" name="name" required /><br /><br />

      <label for="price">Price:</label>
      <input
        type="number"
        id="price"
        name="price"
        step="0.01"
        required
      /><br /><br />

      <label for="productYear">Product Year:</label>
      <input
        type="number"
        id="productYear"
        name="productYear"
        required
      /><br /><br />

      <label for="image">Image URL:</label>
      <input type="text" id="image" name="image" /><br /><br />

      <label for="category">Category:</label>
      <select id="category" name="category" required>
        <c:forEach items="${categories}" var="category">
          <option value="${category.id}">${category.name}</option>
        </c:forEach></select
      ><br /><br />

      <input type="hidden" name="action" value="add" />
      <button type="submit">Add Product</button>
    </form>
  </body>
</html>
