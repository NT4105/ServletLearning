package controllers;

import entities.Account;
import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import dto.RegisterDTO;
import exceptions.ValidationException;
import exceptions.InvalidDataException;

public class AuthController extends HttpServlet {
    private final String LIST = "Product";
    private final String LIST_VIEW = "view/product/list.jsp";
    private final String LOGIN_VIEW = "view/account/login.jsp";
    private final String REGISTER_VIEW = "view/account/register.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equals("register")) {
            req.getRequestDispatcher(REGISTER_VIEW).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action != null && action.equals("register")) {
            register(req, resp);
        } else {
            login(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getByUsernamePassword(username, password);
        if (account != null) {
            req.getSession().setAttribute("account", account);
            resp.sendRedirect(LIST);
        } else {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        RegisterDTO dto = new RegisterDTO(username, password, role);
        try {
            dto.validate();

            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.register(username, password, Integer.parseInt(role));

            if (success) {
                req.setAttribute("message", "Registration successful! Please login.");
                req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
            } else {
                throw new InvalidDataException("Registration failed. Username might be taken.");
            }
        } catch (ValidationException | InvalidDataException ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher(REGISTER_VIEW).forward(req, resp);
        }
    }
}
