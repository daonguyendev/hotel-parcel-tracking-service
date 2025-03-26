package com.hrs.parceltracking.configuration;

import com.hrs.parceltracking.constant.FlywayConstant;
import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlywayConfigurationTest {

    @Mock
    private Dotenv dotenv;

    @InjectMocks
    private FlywayConfiguration flywayConfiguration;

    @BeforeEach
    void setUp() {
        when(dotenv.get(FlywayConstant.FLYWAY_ON)).thenReturn("true");
        when(dotenv.get(FlywayConstant.FLYWAY_LOC)).thenReturn("classpath:db/migration");
        when(dotenv.get(FlywayConstant.FLYWAY_BS_ON_MIG)).thenReturn("true");
    }

    @Test
    void flyway_ShouldNotCallMigrate_WhenFlywayEnabledIsFalse() {
        when(dotenv.get(FlywayConstant.FLYWAY_ON)).thenReturn("false");

        Flyway flyway = spy(flywayConfiguration.flyway());

        verify(flyway, never()).migrate();
    }

    @Test
    void flyway_ShouldHandleNullDotenvValues() {
        when(dotenv.get(FlywayConstant.FLYWAY_ON)).thenReturn(null);
        when(dotenv.get(FlywayConstant.FLYWAY_BS_ON_MIG)).thenReturn(null);

        Flyway flyway = flywayConfiguration.flyway();

        assertNotNull(flyway);
    }
}
