package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LoginInterceptor;
import hello.exception.resolver.MyHandlerException;
import hello.exception.resolver.UserHandlerException;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import lombok.extern.java.Log;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","*.ico","/error","/error-page/**");
        //인터셉터는 dispatchtype같으네 없으나 그냥 url패턴에서 제외시킴ㄴ된다.
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerException());
        resolvers.add(new UserHandlerException());
    }

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filter=new FilterRegistrationBean<>();
        filter.setFilter(new LogFilter());
        filter.setOrder(1);
        filter.addUrlPatterns("/*");
        filter.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);
        //아무것도 않넣으면 request에만 반응.

        return filter;
    }




}
