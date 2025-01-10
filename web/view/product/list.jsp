<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
    </head>
    <body>
        <h1>Product Page</h1>
        <br>

        <!--SEARCH FORM-->
        <form action="Product" method="GET">
            <label>Product Name:</label>
            <input type="text" name="productName" value="${productName}">

            <label>Category:</label>
            <select name="category" id="category">
                <option value="">All Categories</option>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>

            <button type="submit">Search</button>
        </form>

        <c:if test="${not empty products}">
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Year</th>
                    <th>Image</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
                <c:forEach items="${products}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td>${p.price}</td>
                        <td>${p.productYear}</td>
                        <td>${p.image}</td>
                        <td>${p.category.name}</td>
                        <td>
                            <form action="Product" method="GET" style="display: inline;">
                                <input type="hidden" name="action" value="prepare-update">
                                <input type="hidden" name="id" value="${p.id}">
                                <button type="submit">Edit</button>
                            </form>
                            <a href="Product?action=confirm-delete&id=${p.id}" class="btn-delete">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <br>
        <form action="Product" method="GET">
            <input type="hidden" name="action" value="prepare-add">
            <button type="submit">Add</button>
        </form>

        <script>
            const selectedCategoryId = '${category}';
            const selectElement = document.getElementById('category');
            
            if (selectedCategoryId) {
                selectElement.value = selectedCategoryId;
            } else {
                selectElement.value = "";
            }
        </script>
    </body>
</html>
