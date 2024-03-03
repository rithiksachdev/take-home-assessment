package medical_uservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("patients")
			.packagesToScan("medical_uservice.controller") // Adjust the package to where your controllers are
			.build();
	}
}
