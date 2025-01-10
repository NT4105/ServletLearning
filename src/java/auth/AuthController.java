package auth;

import auth.dtos.LoginDTO;
import auth.dtos.RegisterDTO;
import dao.AccountDAO;
import entities.Account;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = { "/Auth" })
public class AuthController extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            request.getRequestDispatcher("/view/account/register.jsp").forward(request, response);
        } else if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("Auth");
        } else {
            request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RegisterDTO dto = new RegisterDTO(
                    request.getParameter("username"),
                    request.getParameter("password"),
                    0 // Force role to CUSTOMER (0)
            );

            if (accountDAO.register(dto)) {
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/view/account/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("/view/account/register.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LoginDTO dto = new LoginDTO(
                    request.getParameter("username"),
                    request.getParameter("password"));

            Account account = accountDAO.getUserInfo(dto.getUsername(), dto.getPassword());

            if (account != null) {
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("account", account);

                // Redirect to products page
                response.sendRedirect("Product");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
        }
    }
}
