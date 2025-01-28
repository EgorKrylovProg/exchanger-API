package Controller.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    private final String[] allowedOrigins = {"http://localhost"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String origin = httpServletRequest.getHeader("Origin");
        if (isAllowedDomain(origin)) {
            httpServletResponse.addHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH");

            if (httpServletRequest.getMethod().equals("OPTIONS")) {
                httpServletResponse.setStatus(200);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private boolean isAllowedDomain(String origin) {
        for (String allowedOrigin: allowedOrigins) {
            if (allowedOrigin.equals(origin)) {
                return true;
            }
        }
        return false;
    }
}
