package com.hrs.parceltracking.configuration;

import com.hrs.parceltracking.constant.FlywayConstant;
import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {
    private final DataSource dataSource;
    private final Dotenv dotenv;

    public FlywayConfiguration(DataSource dataSource, Dotenv dotenv) {
        this.dataSource = dataSource;
        this.dotenv = dotenv;
    }

    @Bean
    public Flyway flyway() {
        String isFlywayEnabled = dotenv.get(FlywayConstant.FLYWAY_ON);
        String flywayLocation = dotenv.get(FlywayConstant.FLYWAY_LOC);
        String isFlywayMigrationOn = dotenv.get(FlywayConstant.FLYWAY_ON);

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
