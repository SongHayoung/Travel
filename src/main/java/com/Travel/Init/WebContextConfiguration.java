package com.Travel.Init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;


@Configuration
@ComponentScan(basePackages = {"com.Travel.biz.MyPage.*"})
@EnableWebMvc
public class WebContextConfiguration implements WebMvcConfigurer {
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
     ** 로케일 리졸버
     **/
    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    /**
     ** 디바이스 정보 인터셉터 추가
     **/
    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    /**
     ** 뷰 리졸버 설정
     **/
    @Bean
    public LiteDeviceDelegatingViewResolver liteDeviceAwareViewResolver() {
        InternalResourceViewResolver delegate = new InternalResourceViewResolver();
        delegate.setPrefix("/WEB-INF/views/");
        delegate.setSuffix(".jsp");
        LiteDeviceDelegatingViewResolver resolver = new LiteDeviceDelegatingViewResolver(delegate);
        resolver.setMobilePrefix("mobile/");
        resolver.setEnableFallback(true);   //모바일 전용 페이지 탐색 실패시 기본 페이지 리턴
        return resolver;
    }

    /**
     ** 파일 업로드 처리
     **/
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
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
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/META-INF/webresources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
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

