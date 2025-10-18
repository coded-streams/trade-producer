package com.codedstreams.tradeproducer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8086}")
    private String serverPort;

    @Bean
    public OpenAPI outboxDemoOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:" + serverPort);
        server.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setEmail("codedstreams@gmail.com");
        contact.setName("Coded Streams");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Trades order simmulator")
                .version("1.0.0")
                .contact(contact)
                .description("""
                        This API demonstrates the a typical order service in a crypto trading firm.
                        
                        ## Key Features:
                        - 🛒 Order management with transactional consistency
                        - 📨 Reliable event publishing of trade events to Kafka
                        - 🔄 Avro event mapping serialization across the pipeline
                        - 🚀 distributed architecture
                                            """)
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
