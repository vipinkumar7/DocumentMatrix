package com.sentinel.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.rest.handlers.AuthFailureHandler;
import com.sentinel.rest.handlers.AuthSuccessHandler;
import com.sentinel.rest.handlers.HttpAuthenticationEntryPoint;
import com.sentinel.rest.handlers.HttpLogoutSuccessHandler;
import com.sentinel.service.UserProfileService;


/**
 * 
 * @author Vipin Kumar
 * @created 29-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 *
 */
@Configuration
@ComponentScan ( basePackages = { "com.sentinel.service" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( SecSecurityConfig.class );

    @Autowired
    private UserRepository userRepository;


    private UserProfileService userProfileService;

    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private HttpAuthenticationEntryPoint httpAuthenticationEntryPoint;

    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;


    @Autowired
    private HttpLogoutSuccessHandler httpLogoutSuccessHandler;


    public SecSecurityConfig()
    {
        super( true );
    }


    @PostConstruct
    void init()
    {
        userProfileService = new UserProfileService( userRepository );
        tokenAuthenticationService = new TokenAuthenticationService( "tooManySecrets", userProfileService );

    }


    @Override
    protected void configure( final AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.authenticationProvider( authProvider() );
    }


    @Override
    public void configure( final WebSecurity web ) throws Exception
    {
        web.ignoring().antMatchers( "/resources/**" );
    }


    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        LOG.trace( "Method: authenticationManagerBean called." );

        return super.authenticationManagerBean();

    }


    @Override
    protected void configure( final HttpSecurity http ) throws Exception
    {
        // @formatter:off
		http.exceptionHandling().and()
        .anonymous().and()
        .servletApi().and()
        .headers().cacheControl().and()
        .authorizeRequests()
        
        .antMatchers( HttpMethod.POST, "/api/login" ).permitAll()
        .antMatchers("/admin/orient/api**").hasRole("ADMIN_ORIENT")
				.and()
				// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userProfileService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
				
		// @formatter:on
    }


    // beans

    @Bean
    public DaoAuthenticationProvider authProvider()
    {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService() );
        authProvider.setPasswordEncoder( justEncoder() );
        return authProvider;
    }


    @Bean
    public PasswordEncoder justEncoder()
    {
        return new BCryptPasswordEncoder( 11 );
    }


    @Bean
    @Override
    public UserDetailsService userDetailsService()
    {
        return userProfileService;
    }


    @Bean
    public TokenAuthenticationService tokenAuthenticationService()
    {
        return tokenAuthenticationService;
    }

}