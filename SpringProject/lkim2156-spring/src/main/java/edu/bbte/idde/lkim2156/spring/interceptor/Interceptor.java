package edu.bbte.idde.lkim2156.spring.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class Interceptor implements WebMvcConfigurer {
    @Autowired
    InterceptorEndpoints interceptorEndpoints;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorEndpoints);

    }
}
