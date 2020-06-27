package com.Travel.Init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.Travel.biz.UserService.*"})
public class UserServiceWebContextConfiguration implements WebMvcConfigurer {
    /**
     ** 뷰 리졸버 설정
     **/
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/",".jsp");
    }

    /**
     ** 로케일 메세지를 위한 인터셉터 설정
     **/
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     ** 로케일 인터셉터 추가
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     ** url 리소스 맵핑
     ** 정적 자원 요청 url은 우선적으로 이쪽으로 들어온다 304 응답코드를 효과적으로 처리해준다
     **/
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/META-INF/webresources/");
    }

    /**
     ** restful url을 위한 정적 리소스 맵핑
     ** 주소에 따른 컨트롤러가 없으면 스태틱 리소스라고 판단한다
     **/
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     ** 메세지 컨버터 등록
     ** 디폴트는 무시된다 (byte[], String ...)
     **/
    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}

