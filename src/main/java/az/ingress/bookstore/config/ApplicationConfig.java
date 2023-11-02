package az.ingress.bookstore.config;

import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.exception.AuthorNotFoundException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final AuthorRepository authorRepository;

    public ApplicationConfig(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> authorRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorNotFoundException(
                        HttpStatus.NOT_FOUND.name(), ErrorMessage.AUTHOR_NOT_FOUND));

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager manager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
