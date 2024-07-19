package qa.guru.rococo.config;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import qa.guru.rococo.service.SpecificRequestDumperFilter;
import qa.guru.rococo.service.cors.CookieCsrfFilter;
import qa.guru.rococo.service.cors.CorsCustomizer;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${rococo-front.base-uri}")
    String rococoFrontUri;

    @Autowired
    private CorsCustomizer corsCustomizer;

    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        corsCustomizer.corsCustomizer(http);

        if (environment.acceptsProfiles(Profiles.of("local", "staging"))) {
            http.addFilterBefore(new SpecificRequestDumperFilter(
                    new RequestDumperFilter(),
                    "/login", "/oauth2/.*"
            ), DisableEncodeUrlFilter.class);
        }

        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/register", "/images/**", "/styles/**", "/fonts/**", "/actuator/health")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                )
                .sessionManagement(sm ->
                        sm.invalidSessionUrl("/login")
                );

        return http.formLogin(Customizer.withDefaults()).build();
    }
}