package Controller.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class HeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        servletResponse.setContentType("application/json;charset=UTF-8");
        filterChain.doFilter(servletRequest, httpServletResponse);
    }
}
