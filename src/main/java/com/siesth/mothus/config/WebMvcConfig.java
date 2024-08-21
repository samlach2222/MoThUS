package com.siesth.mothus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

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
     *
     * @return the locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        // Use HTML Accept-Language header to determine the locale
        // This resolver does not support LocaleChangeInterceptor (at least not the "?lang=..." url parameter)
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }
}