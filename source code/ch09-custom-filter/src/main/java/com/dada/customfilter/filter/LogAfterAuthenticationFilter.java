package com.dada.customfilter.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author dada
 * @date 2022/5/19-12:51
 */
//@Component
public class LogAfterAuthenticationFilter implements Filter {
    Logger logger = Logger.getLogger(LogAfterAuthenticationFilter.class.getName());

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain filterChain)
            throws IOException, ServletException {

        String requestId = ((HttpServletRequest)request).getHeader("Request-Id");
        logger.info("requestId:"+requestId+" finish authentication");

        filterChain.doFilter(request, response);
    }
}
