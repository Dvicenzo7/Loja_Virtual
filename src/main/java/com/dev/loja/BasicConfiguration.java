//package com.dev.loja;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class BasicConfiguration {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Se você estiver usando um banco de dados
//        if (dataSource != null) {
//            auth.jdbcAuthentication()
//                    .dataSource(dataSource)
//                    .usersByUsernameQuery(
//                            "select email as username, senha as password, 1 as enabled from funcionario where email = ?"
//                    )
//                    .authoritiesByUsernameQuery(
//                            "select f.email as username, p.nome as authority from funcionario f " +
//                                    "inner join permissoes_funcionario pf on f.id = pf.funcionario_id " +
//                                    "inner join papel p on pf.papel_id = p.id where f.email = ?"
//                    )
//                    .passwordEncoder(passwordEncoder());
//        } else {
//            // Em caso de não ter o DataSource configurado, use a autenticação em memória
//            auth.inMemoryAuthentication()
//                    .withUser("user").password(passwordEncoder().encode("123")).roles("USER")
//                    .and()
//                    .withUser("ADMIN").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
//        }
//    }
//
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/administrativo/entrada/**").hasRole("gerente")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login").permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/administrativo")
//                )
//                .exceptionHandling(exception -> exception
//                        .accessDeniedPage("/negado")
//                );
//    }
//}
