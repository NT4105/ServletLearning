<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Delete Product</title>
        <style>
            .product-details {
                margin: 20px 0;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .button-group {
                margin-top: 20px;
            }
            .button-group button {
                margin-right: 10px;
                padding: 5px 15px;
            }
            .warning {
                color: red;
                font-weight: bold;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <h1>Confirm Delete Product</h1>
        
        <div class="warning">
            Warning: This action cannot be undone!
        </div>
        
        <div class="product-details">
            <h2>Product Details:</h2>
            <p><strong>Name:</strong> ${product.name}</p>
            <p><strong>Price:</strong> ${product.price}</p>
            <p><strong>Year:</strong> ${product.productYear}</p>
            <p><strong>Category:</strong> ${product.category.name}</p>
            
            <c:if test="${not empty product.image}">
                <img src="${product.image}" alt="Product Image" style="max-width: 200px;">
            </c:if>
        </div>

        <div class="button-group">
            <form action="Product" method="POST" style="display: inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="${product.id}">
                <button type="submit" style="background-color: #ff4444; color: white;">
                    Confirm Delete
                </button>
            </form>
            
            <a href="Product" style="text-decoration: none;">
                <button type="button">
                    Cancel
                </button>
            </a>
        </div>
    </body>
</html> 