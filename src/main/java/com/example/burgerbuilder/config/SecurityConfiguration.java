package com.example.burgerbuilder.config;

import com.example.burgerbuilder.security.jwt.JWTConfigurer;
import com.example.burgerbuilder.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/*
 * Notes:
 *   1. Only @Configuration in class SecurityConfiguration is enough to enable Spring Security because of Spring Boot Auto Config
 *           => class SecurityAutoConfiguration & class SecurityFfilterAutoConfiguration
 *   2. Use @EnableWebMvc to @Import Web Security Class ( not related to Spring Boot Auto Configuration )
 *   3. Auto Configuration will create a default implementation for the abstract class WebSecurityConfigurationAdapter,
 *      if your SecurityConfiguration extends WebSecurityConfigurationAdapter, the default implementation of WebSecurityConfigurationAdapter Bean will not be enabled
 *   4. @ConditionalOnClass will detect the classpath to find the specific class
 *   5. Actually, in Spring Boot Auto Config, the annotation @EnableWebSecurity is optional,
 *      because class SecurityAutoConfiguration imports SpringBootWebSecurityConfiguration.class, WebSecurityEnablerConfiguration.class,
 *      SecurityDataConfiguration.class . WebSecurityEnablerConfiguration is marked with @EnableWebSecurity & @Configuration
 *   6. jdbcAuthentication will erase the existing dataSource, so you need to manually add dataSource() after it.
 * */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    // Don't add the default constructor if you intend to mark the instance members final, otherwise it throws exception.
    // public SecurityConfiguration() { }
    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, DataSource dataSource, UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @PostConstruct
    public void init() {
        // configure authenticationManagerBuilder & userDetailService here.
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService) // This will activate DaoAuthentication by default
                    .passwordEncoder(spring4PasswordEncoder());
            //authenticationManagerBuilder
            //        .jdbcAuthentication()
            //        .dataSource(dataSource)// Refer to #6
            //        .passwordEncoder(spring4PasswordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Autowired
    public void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // this is the short form for private final AuthenticationManagerBuilder && Autowiring Constructor && @PostConstruct init
        // configure authenticationManagerBuilder & userDetailService here.
    }

    @Bean(name = "noOpPasswordEncoder")
    @Primary
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // this is for demo purpose.
    }

    @Bean(name = "spring4PasswordEncoder")
    public PasswordEncoder spring4PasswordEncoder() {
        return new BCryptPasswordEncoder(); // for Spring Security 4, simply return BCrypt Password Encoder
    }

    @Bean(name = "spring5PasswordEncoder")
    public PasswordEncoder spring5PasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // this is for Spring Security 5
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/h2-console/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // http.apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}