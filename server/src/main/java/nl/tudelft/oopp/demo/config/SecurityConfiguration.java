package nl.tudelft.oopp.demo.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaRepositories
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/login", "/images/**",
                        "/rest/users/image/downloadFile/**",
                        "/buildings/image/downloadFile/**",
                        "/rooms/image/downloadFile/**").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers("/rest/users/image/**")
                    .hasAnyAuthority("Student", "Admin", "Employee")
                .antMatchers(HttpMethod.GET, "/buildings/**", "/rooms/**")
                    .hasAnyAuthority("Student", "Admin", "Employee")
                .antMatchers(HttpMethod.POST, "/buildings/**", "/rooms/**").hasAuthority("Admin")
                .antMatchers("/admin", "/admin/**",
                        "/buildings/add", "/buildings/delete",
                        "/rooms/add", "/rooms/delete", "/rest/users/all").hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE, "/rest/users/**").hasAnyAuthority("Admin")
                .antMatchers("/employee", "/employee/**").hasAnyAuthority("Employee", "Admin")
                .antMatchers("/**").hasAnyAuthority("Student", "Admin", "Employee")
                .and().httpBasic()
                .and().cors().and()
                .csrf().disable();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
