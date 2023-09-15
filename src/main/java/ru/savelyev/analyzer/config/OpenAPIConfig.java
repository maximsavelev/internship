package ru.savelyev.analyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Value("${openapi.dev-url}")
    private String devUrl;
    @Value("${api.description}")
    private String description;
    @Value("${api.title}")
    private String title;
    @Value("${api.version}")
    private String version;
    @Value("${api.developer.name}")
    private String name;
    @Value("${api.developer.mail}")
    private String mail;
    @Value("${api.server.name}")
    private String serverName;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription(serverName);

        Contact contact = new Contact();
        contact.setEmail(mail);
        contact.setName(name);

        Info info = new Info()
                .title(title)
                .version(version)
                .description(description);

        return new OpenAPI().info(info).addServersItem(devServer);
    }
}
