package medical_uservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*") // Allows CORS requests from any origin
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // You can adjust the allowed methods as necessary
            .allowedHeaders("*");
    }
}
