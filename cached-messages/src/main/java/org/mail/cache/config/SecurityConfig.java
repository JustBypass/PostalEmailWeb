package org.mail.cache.config;

import com.global.filters.JwtCheckFilter;
import com.mail.global.methods.WithStreamMethods;
import org.mail.cache.utils.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] whiteList = {
        "operations/**",
            "cache/**",
            "search/local/**",
    };
    @Autowired
    private Property property;

    @Bean
    public WithStreamMethods withStreamMethods(){
        return new WithStreamMethods();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.authorizeRequests(
                exchanges->
                        exchanges
                                .antMatchers(whiteList)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
        );
        http.addFilterBefore(new JwtCheckFilter(property.getAuthUrl()), UsernamePasswordAuthenticationFilter.class);
    }

}
