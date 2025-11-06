package ru.javaops.restaurantvoting.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import jakarta.annotation.PostConstruct;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

import javax.money.MonetaryAmount;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = """
                        Приложение RESTAURANT VOTING</a>
                        <p><b>Тестовые креденшелы:</b><br>
                        - user@yandex.ru / password<br>
                        - admin@gmail.com / admin<br>
                        - guest@gmail.com / guest</p>
                        """,
                contact = @Contact(name = "Kazbek Tedeev", email = "tedeev@kazbek.pro")
        ),
        security = @SecurityRequirement(name = "basicAuth"),
        tags = {
                @Tag(name = "Admin User Controller"),
                @Tag(name = "Meal Admin Controller"),
                @Tag(name = "Restaurant Admin Controller"),
                @Tag(name = "Menu Admin Controller"),
                @Tag(name = "Vote Admin Controller"),
                @Tag(name = "Profile Controller"),
                @Tag(name = "Menu Controller"),
                @Tag(name = "Restaurant Controller"),
                @Tag(name = "Menu Controller"),
                @Tag(name = "Vote Profile Controller")
        }
)
public class OpenApiConfig {
    @PostConstruct
    public void init() {
        SpringDocUtils.getConfig().replaceWithSchema(MonetaryAmount.class,
                new ObjectSchema()
                        .addProperty("amount", new NumberSchema().example(149.99))
                        .addProperty("currency", new StringSchema().example("RUB")));
    }
}
