/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import auth.dtos.LoginDTO;
import auth.dtos.RegisterDTO;
import dao.AccountDAO;
import entities.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AuthController", urlPatterns = { "/Auth" })
public class AuthController extends HttpServlet {

    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chỉ xử lý hiển thị form
        String page = "/view/account/login.jsp"; // Default page

        String action = request.getParameter("action");
        if ("register".equals(action)) {
            page = "/view/account/register.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "";

        try {
            switch (action) {
                case "login":
                    url = processLogin(request);
                    break;
                case "register":
                    url = processRegister(request);
                    break;
                case "logout":
                    url = processLogout(request);
                    break;
                default:
                    url = "/view/account/login.jsp";
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            url = "/view/account/login.jsp";
        }

        // Kiểm tra nếu là redirect
        if (url.contains("redirect:")) {
            response.sendRedirect(url.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String processLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginDTO loginDTO = new LoginDTO(username, password);
        Account account = accountDAO.getUserInfo(loginDTO.getUsername(), loginDTO.getPassword());

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            return "redirect:Product";
        } else {
            request.setAttribute("error", "Invalid username or password");
            return "/view/account/login.jsp";
        }
    }

    private String processRegister(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        RegisterDTO registerDTO = new RegisterDTO(username, password, 0);

        if (accountDAO.register(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getRole())) {
            request.setAttribute("success", "Registration successful! Please login.");
            return "/view/account/login.jsp";
        } else {
            request.setAttribute("error", "Registration failed. Username may already exist.");
            return "/view/account/register.jsp";
        }
    }

    private String processLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:Auth";
    }
}
