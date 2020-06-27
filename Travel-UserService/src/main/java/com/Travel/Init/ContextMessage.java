package com.Travel.Init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ContextMessage {

    //로케일 메세지 프로퍼티 설정
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        // 메세지 프로퍼티파일의 위치와 이름 지정
        source.setBasename("classpath:/properties/userServiceMessage");
        source.setBasename("classpath:/properties/ValidationMessage");
        // 기본 인코딩을 지정
        source.setDefaultEncoding("UTF-8");
        // 프로퍼티 파일의 변경을 감지할 시간 간격을 지정
        source.setCacheSeconds(60);
        // 없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }

    /*
     * 변경된 언어 정보를 기억할 로케일 리졸퍼를 생성
     * 세션에 저장하는 방식을 사용
     */
    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}
