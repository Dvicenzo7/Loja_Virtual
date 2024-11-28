package com.dev.loja;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class BasicConfiguration {

    private final DataSource dataSource;

    public BasicConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Definir o PasswordEncoder como Bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração do filtro de segurança para o "funcionario"
    @Bean
    public SecurityFilterChain securityFilterChainFuncionario(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/login").permitAll()  // Permitir acesso ao login
                        .requestMatchers("/administrativo/cadastrar/**").hasAuthority("gerente")  // Acesso somente para "gerente"
                        .requestMatchers("/administrativo/**").authenticated()  // Qualquer outro acesso requer autenticação
                )
                .formLogin(login -> login
                        .loginPage("/login")  // Página de login personalizada
                        .failureUrl("/login")  // Página de erro de login
                        .loginProcessingUrl("/admin")  // URL de processamento de login
                        .defaultSuccessUrl("/administrativo")  // Página de sucesso após login
                        .usernameParameter("username")  // Parâmetro do nome de usuário
                        .passwordParameter("password")  // Parâmetro da senha
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/administrativo/logout"))  // URL de logout
                        .logoutSuccessUrl("/login")  // Página de sucesso de logout
                        .deleteCookies("JSESSIONID")  // Deletar cookies de sessão
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/negado")  // Página de erro de acesso negado
                )
                .csrf(csrf -> csrf.disable());  // Desabilitar CSRF conforme necessário
        return http.build();
    }

    // Definição do AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // O AuthenticationManagerBuilder é obtido a partir do HttpSecurity
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Configuração do AuthenticationManager com JDBC
        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email as username, senha as password, 1 as enabled from funcionario where email = ?")
                .authoritiesByUsernameQuery("select f.email as username, p.nome as authority from funcionario f " +
                        "inner join permissoes_funcionario pf on f.id = pf.funcionario_id " +
                        "inner join papel p on pf.papel_id = p.id where f.email = ?")
                .passwordEncoder(passwordEncoder());  // Definir o password encoder como BCrypt

        return authenticationManagerBuilder.build();  // Retorna o AuthenticationManager
    }
}
