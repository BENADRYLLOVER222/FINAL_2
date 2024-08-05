package first.second.third.fuckmylife.filter;

import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BlacklistFilter implements Filter {

    private final UserService userService;

    @Autowired
    public BlacklistFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

        // Исключаем пути логина и регистрации
        if (path.equals(httpRequest.getContextPath() + "/") ||
                path.equals(httpRequest.getContextPath() + "/login") ||
                path.equals(httpRequest.getContextPath() + "/registration") ||
                path.equals(httpRequest.getContextPath() + "/auth") ||
                path.equals(httpRequest.getContextPath() + "/register") ||
                path.equals(httpRequest.getContextPath() + "/error")) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user != null) {
            if ("MUTED".equalsIgnoreCase(userService.getByUsername(user.getUsername()).getRole())) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/error?message=You are banned");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
