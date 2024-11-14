//package com.dev.loja;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication().withUser("user")
//                .password(new BCryptPasswordEncoder().encode("123")).roles("USER")
//                .and().withUser("ADMIN").password(new BCryptPasswordEncoder()
//                        .encode("admin")).roles("USER", "ADMIN");
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select email as username, senha as password, 1 as enable from funcionario where email=?"
//                ).authoritiesByUsernameQuery(
//                        "select funcionario.email as username, papel.nome as authority from permissoes_funcionario inner join papel on permissoes.papel_id = papel.id where funcionario.email=?"
//                ).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable().authorizeRequests()
//                .antMatches("/administrativo/entrada/**").hasAnyRole("gerente").and().formLogin()
//                .loginPage("/login").permitAll().and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/administrativo").and()
//                .exceptionHandling().accessDeniedPage("/negado");
//    }
//}
