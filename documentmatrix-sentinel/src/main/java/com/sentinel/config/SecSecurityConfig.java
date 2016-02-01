package com.sentinel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


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
// TODO
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private UserDetailsService userDetailsService;


    // @Autowired
    // private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    // @Autowired
    // private AuthenticationFailureHandler authenticationFailureHandler;

    public SecSecurityConfig()
    {
        super();
    }


    @Override
    protected void configure( final AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.eraseCredentials( false );// so we can read them in controllers
        auth.authenticationProvider( authProvider() );
    }


    @Override
    public void configure( final WebSecurity web ) throws Exception
    {
        web.ignoring().antMatchers( "/resources/**" );
    }


    @Override
    protected void configure( final HttpSecurity http ) throws Exception
    {
        // @formatter:off
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/login*", "/login*", "/logout*", "/signin/**",
						"/signup/**")
				.permitAll()
				.antMatchers("/invalidSession*")
				.anonymous()
				// .antMatchers( "/graph/*").access(
				// "hasRole('ROLE_RIGHT_access_management_screens" )
				// .antMatchers( "/graph/**").hasRole( "READ_PRIVILEGE" )
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				// .loginPage("/login")
				.defaultSuccessUrl("/homepage.html")
				.failureUrl("/login?error=true")
				// .successHandler(myAuthenticationSuccessHandler)
				// .failureHandler(authenticationFailureHandler)
				.permitAll().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.sessionFixation().newSession()//node in case application define this url
				//.invalidSessionUrl("/invalidSession.html")
				.and().logout().invalidateHttpSession(false)
				.logoutSuccessUrl("/logout.html?logSucc=true")
				.deleteCookies("JSESSIONID").permitAll();
		// @formatter:on
    }


    // beans

    @Bean
    public DaoAuthenticationProvider authProvider()
    {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService );
        authProvider.setPasswordEncoder( encoder() );
        return authProvider;
    }


    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder( 11 );
    }

}