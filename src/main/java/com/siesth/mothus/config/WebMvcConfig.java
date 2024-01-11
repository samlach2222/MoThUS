package com.siesth.mothus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * This class is used to configure the locale resolver and interceptor.
 * The locale resolver is used to resolve the locale from the request header.
 * The locale interceptor is used to change the locale based on the request parameter.
 * The locale resolver and interceptor are used to support internationalization.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * This method is used to configure the locale resolver.
     * @return the locale resolver
     */
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH); // Set English as the default locale
        return resolver;
    }

    /**
     * This method is used to add an interceptor to the interceptor registry.
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }
}