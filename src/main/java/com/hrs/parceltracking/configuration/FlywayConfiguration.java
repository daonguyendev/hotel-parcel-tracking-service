package com.hrs.parceltracking.configuration;

import com.hrs.parceltracking.constant.FlywayConstant;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfiguration {
    private final DataSource dataSource;
    private final Dotenv dotenv;

    @Bean
    public Flyway flyway() {
        String isFlywayEnabled = dotenv.get(FlywayConstant.FLYWAY_ON);
        String flywayLocation = dotenv.get(FlywayConstant.FLYWAY_LOC);
        String isFlywayMigrationOn = dotenv.get(FlywayConstant.FLYWAY_BS_ON_MIG);

        Flyway flyway = Flyway.configure()
                .dataSource(this.dataSource)
                .locations(flywayLocation)
                .baselineOnMigrate(Boolean.parseBoolean(isFlywayMigrationOn))
                .load();
        if(Boolean.parseBoolean(isFlywayEnabled)){
            flyway.migrate();
        }
        return flyway;
    }
}
