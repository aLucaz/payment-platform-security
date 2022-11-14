package pe.client.custom.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(
        @Value("${spring.application.name}") String artifact,
        @Value("${application.description}") String appDescription,
        @Value("${application.version}") String appVersion
    ) {
        return new OpenAPI().info(new Info()
            .title(artifact)
            .version(appVersion)
            .description(appDescription)
            .contact(new Contact()
                .email("arturo.lucas.pe@gmail.com")
                .url("https://www.linkedin.com/in/arturo-lucas/"))
            .license(new License()
                .name("Apache 2.0")
                .url("https://springdoc.org")));
    }

}
