package first.second.third.fuckmylife.filter;

import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminFilter implements Filter {

    private final UserService userService;

    @Autowired
    public AdminFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute("user");

        if (user != null && userService.getByUsername(user.getUsername()).getRole().equalsIgnoreCase("admin")) {
            chain.doFilter(request, response);
            return;
        }

        // Handle unauthorized access, for example, by redirecting to a login page or error page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
    }
}