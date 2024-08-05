package first.second.third.fuckmylife.filter;

import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.service.Impl.UserServiceImpl;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    private final UserService userService;

    @Autowired
    public AuthenticationFilter(UserService userService) {
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

        // Exclude login and registration paths
        if (path.equals(httpRequest.getContextPath() + "/") || path.equals(httpRequest.getContextPath() + "/login") || path.equals(httpRequest.getContextPath() + "/registration")
        || path.equals(httpRequest.getContextPath() + "/auth") || path.equals(httpRequest.getContextPath() + "/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is authenticated
        if (httpRequest.getSession().getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("rememberMe")){
                        try {
                            User user = userService.getByUsername(cookie.getValue());
                            httpRequest.getSession().setAttribute("user", user);
                            chain.doFilter(request, response);
                            return;
                        }catch(ServiceException e) {
                            }
                        }
                    }
            }
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
