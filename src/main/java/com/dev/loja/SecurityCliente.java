import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityCliente {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChainCliente(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/finalizar/**").hasAuthority("cliente")  // Permissões para /finalizar/**
                        .anyRequest().permitAll()  // Outras requisições são permitidas
                )
                .csrf(csrf -> csrf.disable())  // Desabilitando CSRF
                .formLogin(login -> login
                        .loginPage("/cadastrar")  // Página de login personalizada
                        .permitAll()  // Permitindo o acesso à página de login
                        .failureUrl("/cadastrar")  // Página de erro de login
                        .loginProcessingUrl("/finalizar")  // URL de processamento de login
                        .successHandler(customAuthenticationSuccessHandler())  // Redirecionamento personalizado
                        .usernameParameter("username")  // Nome do parâmetro de username
                        .passwordParameter("password")  // Nome do parâmetro de senha
                )
                .logout(logout -> logout
                        .logoutUrl("/finalizar/logout")  // URL de logout personalizada
                        .logoutSuccessUrl("/")  // Página de sucesso de logout
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/negado")  // Página de erro de acesso negado
                );
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/finalizar");  // Defina a URL para redirecionar após o login
        successHandler.setAlwaysUseDefaultTargetUrl(true);  // Força o redirecionamento para /finalizar
        return successHandler;
    }
}
