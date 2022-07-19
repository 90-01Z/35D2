package melopee;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(Application.class);
    }
    
    @Bean
    public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
            throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        String dossier=System.getProperty("catalina.base");
        ppc.setLocations(new PathMatchingResourcePatternResolver().getResources("file:"+dossier+"/webapps/*.properties"));
        return ppc;
    }
}
