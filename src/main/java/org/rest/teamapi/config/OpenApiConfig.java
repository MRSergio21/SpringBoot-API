package org.rest.teamapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Team Performance API",
                version = "1.1",
                description = "API for Champions League context, this API allows CRUD operations on teams and players, as well as managing player signings between teams.",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Sergio Mayén",
                        email = "sergio@gmail.com"
                )
        )
)

public class OpenApiConfig {

}
