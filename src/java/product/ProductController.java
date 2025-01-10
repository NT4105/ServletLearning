package product;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import product.dtos.CreateProductDTO;
import product.dtos.DeleteProductDTO;
import product.dtos.UpdateProductDTO;
import product.dtos.SearchProductDTO;

import java.util.List;
import dao.ProductDAO;
import entities.Product;

@WebServlet(name = "ProductController", urlPatterns = { "/Product" })
public class ProductController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Auth");
            return;
        }

        String action = request.getParameter("action");
        String url = "/view/product/list.jsp"; // Default view

        try {
            switch (action == null ? "list" : action) {
                case "create":
                    url = showCreateForm(request);
                    break;
                case "edit":
                    url = showEditForm(request);
                    break;
                case "delete":
                    url = deleteProduct(request);
                    break;
                default:
                    url = listProducts(request);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error: " + ex.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Auth");
            return;
        }

        String action = request.getParameter("action");
        String url = "/view/product/list.jsp";

        try {
            switch (action) {
                case "add":
                    url = createProduct(request);
                    break;
                case "update":
                    url = updateProduct(request);
                    break;
                default:
                    url = listProducts(request);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error: " + ex.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String listProducts(HttpServletRequest request) {
        String productName = request.getParameter("name");
        String categoryIdStr = request.getParameter("category");
        String priceStr = request.getParameter("price");
        String filterBy = request.getParameter("filterBy"); // equal, greater, less

        Integer categoryId = null;
        Float price = null;

        try {
            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr);
            }
            if (priceStr != null && !priceStr.isEmpty()) {
                price = Float.parseFloat(priceStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            return "/view/product/list.jsp";
        }

        SearchProductDTO searchDTO = new SearchProductDTO(productName, categoryId, price, filterBy);
        List<Product> products = productDAO.search(searchDTO);

        request.setAttribute("products", products);
        return "/view/product/list.jsp";
    }

    private String showCreateForm(HttpServletRequest request) {
        return "/view/product/create.jsp";
    }

    private String createProduct(HttpServletRequest request) {
        try {
            CreateProductDTO dto = new CreateProductDTO(
                    request.getParameter("name"),
                    request.getParameter("price"),
                    request.getParameter("productYear"),
                    request.getParameter("image"),
                    request.getParameter("category"));

            if (productDAO.create(dto)) {
                request.setAttribute("success", "Product created successfully");
                return listProducts(request);
            } else {
                request.setAttribute("error", "Failed to create product");
                return showCreateForm(request);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            return showCreateForm(request);
        }
    }

    private String showEditForm(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.getById(id);
            if (product != null) {
                request.setAttribute("product", product);
                return "/view/product/edit.jsp";
            } else {
                request.setAttribute("error", "Product not found");
                return listProducts(request);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID");
            return listProducts(request);
        }
    }

    private String updateProduct(HttpServletRequest request) {
        try {
            UpdateProductDTO dto = new UpdateProductDTO(
                    request.getParameter("id"),
                    request.getParameter("name"),
                    request.getParameter("price"),
                    request.getParameter("productYear"),
                    request.getParameter("image"),
                    request.getParameter("category"));

            if (productDAO.update(dto)) {
                request.setAttribute("success", "Product updated successfully");
                return listProducts(request);
            } else {
                request.setAttribute("error", "Failed to update product");
                return showEditForm(request);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            return showEditForm(request);
        }
    }

    private String deleteProduct(HttpServletRequest request) {
        try {
            DeleteProductDTO dto = new DeleteProductDTO(request.getParameter("id"));
            if (productDAO.delete(dto)) {
                request.setAttribute("success", "Product deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete product");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID");
        }
        return listProducts(request);
    }
}