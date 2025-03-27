package com.everspark.parceltracking.configuration;

import com.everspark.parceltracking.constant.EnvConstant;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfiguration {

    @Value(EnvConstant.ACTIVE_PROFILE)
    private String activeProfile;

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory(EnvConstant.DOT_ENV_DIR_ROOT_LEVEL)
                .filename(EnvConstant.DOT_ENV_NAME_PREFIX.concat(activeProfile))
                .load();
    }
}
