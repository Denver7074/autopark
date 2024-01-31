package ru.denver7074.autopark;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Автопарк",
				version = "1.0.0",
				description = "Тестовое задани по управлению автопарком",
				contact = @Contact(name = "Denis",
						email = "denver7074@yandex.ru"
				)
		)
)
public class AutoparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoparkApplication.class, args);
	}

}
