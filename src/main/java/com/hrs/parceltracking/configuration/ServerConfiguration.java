package com.hrs.parceltracking.configuration;

import com.hrs.parceltracking.constant.ServerConstant;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ServerConfiguration {
    private final Dotenv dotenv;

    @Bean
    public ServletWebServerFactory servletContainer() {
        String serverPort = dotenv.get(ServerConstant.SERVER_PORT);
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setPort(Integer.parseInt(Objects.requireNonNull(serverPort)));
        return tomcat;
    }
}
