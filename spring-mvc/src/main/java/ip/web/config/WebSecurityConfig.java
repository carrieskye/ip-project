package ip.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index.htm").permitAll()
                .antMatchers("/css/**", "/images/**").permitAll()
                .antMatchers("/classroom.htm").hasAnyRole("USER", "ADMIN")
                .antMatchers("/classroom/**").hasRole("ADMIN")
                .antMatchers("/course.htm").hasAnyRole("USER", "ADMIN")
                .antMatchers("/course/**").hasRole("ADMIN")
                .antMatchers("/exam.htm").hasAnyRole("USER", "ADMIN")
                .antMatchers("/exam/**").hasRole("ADMIN")
                .antMatchers("/student.htm").hasAnyRole("USER", "ADMIN")
                .antMatchers("/student/**").hasRole("ADMIN")
                .antMatchers("/teacher.htm").hasAnyRole("USER", "ADMIN")
                .antMatchers("/teacher/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.htm").permitAll()
                .loginProcessingUrl("/login").permitAll()
                .defaultSuccessUrl("/index.htm")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index.htm")
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("r0458882").password("ikbencaro").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("t").roles("ADMIN").build());
        return manager;
    }

}
