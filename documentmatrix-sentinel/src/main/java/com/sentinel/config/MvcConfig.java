package com.sentinel.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/**
 * 
 * @author Vipin Kumar
 * @created 29-Jan-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 *
 */
@Configuration
@ComponentScan ( basePackages = { "com.sentinel.web" })
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter
{

    public MvcConfig()
    {
        super();
    }


    //

    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry )
    {
        registry.addResourceHandler( "/resources/**" ).addResourceLocations( "/", "/resources/" );
    }


    @Override
    public void addInterceptors( final InterceptorRegistry registry )
    {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName( "lang" );
        registry.addInterceptor( localeChangeInterceptor );
    }


    // beans

    @Bean
    public ViewResolver viewResolver()
    {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass( JstlView.class );
        bean.setPrefix( "/WEB-INF/view/" );
        bean.setSuffix( ".jsp" );
        return bean;
    }


    @Bean
    public LocaleResolver localeResolver()
    {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale( Locale.ENGLISH );
        return cookieLocaleResolver;
    }


    @Bean
    public MessageSource messageSource()
    {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename( "classpath:messages" );
        messageSource.setUseCodeAsDefaultMessage( true );
        messageSource.setDefaultEncoding( "UTF-8" );
        messageSource.setCacheSeconds( 0 );
        return messageSource;
    }


}