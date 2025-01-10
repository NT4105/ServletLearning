<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
    <body>
        <h1>Update Product</h1>
        
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
        
        <form action="Product" method="POST">
            <input type="hidden" name="id" value="${product.id}">
            
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" value="${product.name}" required><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" value="${product.price}" required><br><br>

            <label for="productYear">Product Year:</label>
            <input type="number" id="productYear" name="productYear" value="${product.productYear}" required><br><br>

            <label for="image">Image URL:</label>
            <input type="text" id="image" name="image" value="${product.image}"><br><br>

            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" 
                            ${category.id == product.category.id ? 'selected' : ''}>
                        ${category.name}
                    </option>
                </c:forEach>
            </select><br><br>
            
            <input type="hidden" name="action" value="update">
            <button type="submit">Update Product</button>
        </form>
    </body>
</html> 