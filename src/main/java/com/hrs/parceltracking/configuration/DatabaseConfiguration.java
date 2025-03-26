package com.hrs.parceltracking.configuration;

import com.hrs.parceltracking.constant.DatabaseConstant;
import com.hrs.parceltracking.constant.EnvConstant;
import com.hrs.parceltracking.constant.HibernateConstant;
import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.constant.PackageConstant;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@DependsOn(EnvConstant.DOT_ENV)
@RequiredArgsConstructor
public class DatabaseConfiguration {
    private final Dotenv dotenv;

    @Bean
    public DataSource dataSource() {
        String dbUrl = buildDatabaseUrl();

        return DataSourceBuilder.create()
                .driverClassName(dotenv.get(EnvConstant.DB_DRIVER))
                .url(dbUrl)
                .username(dotenv.get(EnvConstant.DB_USER))
                .password(dotenv.get(EnvConstant.DB_PASS))
                .build();
    }

    private String buildDatabaseUrl() {
        String dbType = dotenv.get(EnvConstant.DB_TYPE);
        String dbHost = dotenv.get(EnvConstant.DB_HOST);
        String dbPort = dotenv.get(EnvConstant.DB_PORT);
        String dbName = Objects.requireNonNull(
                dotenv.get(EnvConstant.DB_NAME),
                MessageConstant.DB_NAME_IS_REQUIRED);

        return String.format(
                DatabaseConstant.DATA_SOURCE_PREFIX,
                dbType, dbHost, dbPort, dbName,
                DatabaseConstant.CREATE_DB_IF_NOT_EXIST,
                DatabaseConstant.IS_DB_CREATE);
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HibernateConstant.HIB_DIALECT, dotenv.get(EnvConstant.JPA_HIB_DIALECT));
        properties.setProperty(HibernateConstant.HIB_FMT_SQL, dotenv.get(EnvConstant.JPA_HIB_FMT_SQL));
        properties.setProperty(HibernateConstant.HIB_HBM_DDL, dotenv.get(EnvConstant.JPA_HIB_DDL_AUTO));
        properties.setProperty(HibernateConstant.HIB_SHOW_SQL, dotenv.get(EnvConstant.JPA_SHOW_SQL));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(PackageConstant.ENTITY_PACKAGE);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(jpaProperties());

        return em;
    }
}
