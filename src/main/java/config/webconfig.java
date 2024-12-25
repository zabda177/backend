package config;
    import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class webconfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**") // Autoriser les chemins API
                    .allowedOrigins("http://localhost:4200","http://127.0.0.1:4200") // Autoriser l'origine Angular
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP permises
                    .allowCredentials(true); // Autoriser les cookies (si nécessaire)
        }
    }


