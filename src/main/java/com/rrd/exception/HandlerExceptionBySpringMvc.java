package com.rrd.exception;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对异常做日志记录
 * Created by Administrator on 2017/5/3.
 */
@Component
public class HandlerExceptionBySpringMvc implements HandlerExceptionResolver {

    private static Logger logger = Logger.getLogger(HandlerExceptionBySpringMvc.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error(o, e);
        return null;
    }
}
