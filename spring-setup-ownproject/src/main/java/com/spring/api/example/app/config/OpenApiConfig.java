package com.spring.api.example.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Business API",
                version = "1.0.0",
                description = "This API handles users, products, orders and more.",
                contact = @Contact(
                        name = "Tihomir Dimitrov",
                        email = "tishoTest@example.com"
                ),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        tags = {
                @Tag(name = "Users", description = "Operations related to user management"),
                @Tag(name = "Products", description = "Manage products in the catalog"),
                @Tag(name = "Orders", description = "Create and manage customer orders")}
)
public class OpenApiConfig {
}
