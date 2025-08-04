package edu.bbte.idde.lkim2156.spring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class InterceptorEndpoints implements HandlerInterceptor {


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {


        if ("DELETE".equals(request.getMethod())
                || "PUT".equals(request.getMethod())
                || "POST".equals(request.getMethod())
                || "GET".equals(request.getMethod())) {
            log.info("The request is allowed to continue.");
            return true;
        } else {
            log.info("I stop the request from being processed.");
            return false;
        }
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        int status = response.getStatus();

        log.info("HTTP Method: {},  Response Status: {}, URL: {}",
                method, status, url);

    }
}
