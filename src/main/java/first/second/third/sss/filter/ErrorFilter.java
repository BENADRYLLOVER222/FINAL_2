package first.second.third.fuckmylife.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (res.getStatus() == HttpServletResponse.SC_NOT_FOUND
                || res.getStatus() == HttpServletResponse.SC_BAD_REQUEST
                || res.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            req.setAttribute("errorMessage", "Page not found");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}