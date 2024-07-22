package com.tech_symphony.timetable_schedule.system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = false, jsr250Enabled = false)
public class SecurityConfig {
//	private final CustomUserDetailService customUserDetailService;
//	private final OAuth2UserService oAuth2UserService;
//
//	@Bean
//	@Order(1)
//	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//		throws Exception {
//		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
//			.oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
//
//		http
//			// Redirect to the login page when not authenticated from the
//			// authorization endpoint
//			.exceptionHandling((exceptions) -> exceptions
//				.defaultAuthenticationEntryPointFor(
//					new LoginUrlAuthenticationEntryPoint("/login"),
//					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//				)
//			)
//
//			// Accept access tokens for User Info and/or Client Registration
//			.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));
//
//		return http.cors(Customizer.withDefaults()).build();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
		throws Exception {

		http
			.csrf((csrf) -> csrf.disable())

			// using resource server and authorize server in same application
//			.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()))

			// Authorize requests
			.authorizeHttpRequests((authorize) -> authorize
				//Getting public api
				.requestMatchers(HttpMethod.GET, "oups").permitAll()

				//
				.requestMatchers("/", "auth/**").permitAll()
				//swagger docs
				.requestMatchers("/swagger-ui/**", "/v3/**", "/swagger-ui.html", "/openapi-3.0.yml").permitAll()
				.anyRequest()
				.authenticated()

			)

//			.userDetailsService(customUserDetailService)


			// Form login handles the redirect to the login page from the
			// authorization server filter chain
//			.oauth2Login(Customizer.withDefaults())
//			.oauth2Login(oauth2 -> oauth2
//				.userInfoEndpoint(infoEndpoint ->
//					infoEndpoint.userService(oAuth2UserService)))
			.formLogin(Customizer.withDefaults());

		return http.cors(Customizer.withDefaults()).getOrBuild();
	}


	//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.withDefaultPasswordEncoder()
//			.username("user")
//			.password("123")
//			.roles("USER")
//			.build();
//
//		return new InMemoryUserDetailsManager(userDetails);
//	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("*");
//		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);

		return source;
	}

//	@Bean
//	public JwtAuthenticationConverter jwtAuthenticationConverter() {
//
//		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//		grantedAuthoritiesConverter.setAuthorityPrefix("");
//
//		JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
//		authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//		return authConverter;
//	}
}
