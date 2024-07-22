package com.tech_symphony.timetable_schedule.system.config.api;

import com.tech_symphony.timetable_schedule.system.exception.ErrorInfo;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
	name = "security_auth",
	type = SecuritySchemeType.OAUTH2,
	flows = @OAuthFlows(authorizationCode =
	@OAuthFlow(
		authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}"
		, tokenUrl = "${springdoc.oAuthFlow.tokenUrl}")))
@Configuration
@OpenAPIDefinition(
	info = @Info(
		contact = @Contact(
			name = "tech symphony",
			email = "contact.duynguyen@gmail.com",
			url = "https://github.com/TechSymphony/movie-booking-be"
		),
		description = "Open Api for  movie booking app",
		title = "OpenApi movie booking ",
		version = "1.0"
	),
	servers = {@Server(url = "/", description = "Default Server URL")}

)
public class SwaggerConfiguration {
	@Bean
	public OpenApiCustomizer openApiCustomizer() {
		ResolvedSchema errResSchema =
			ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ErrorInfo.class));
		Content content =
			new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(errResSchema.schema));
		return openApi ->
			openApi
				.getPaths()
				.values()
				.forEach(
					pathItem ->
						pathItem
							.readOperations()
							.forEach(
								operation ->
									operation
										.getResponses()
										.addApiResponse(
											"403",
											new ApiResponse()
												.description("Access denied")
												.content(content))
										.addApiResponse(
											"500",
											new ApiResponse()
												.description("Server error")
												.content(content))
										.addApiResponse(
											"400",
											new ApiResponse()
												.description("Bad Request")
												.content(content))
										.addApiResponse(
											"404",
											new ApiResponse()
												.description("Resource Not Found")
												.content(content))));
	}

}
