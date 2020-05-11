package com.hzu.recruit.web.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    //容器启动时执行
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //在方法拦截时执行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("Request--coming");
        //转发到下一个Filter
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //容器销毁时执行
    @Override
    public void destroy() {

    }
}
