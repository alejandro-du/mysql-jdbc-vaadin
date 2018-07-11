package com.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties1() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource1(DataSourceProperties dataSourceProperties1) {
        return dataSourceProperties1.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public NamedParameterJdbcTemplate jdbcTemplate1(DataSource dataSource1) {
        return new NamedParameterJdbcTemplate(dataSource1);
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource2")
    @Qualifier("dataSourceProperties2")
    public DataSourceProperties dataSourceProperties2() {
        return new DataSourceProperties();
    }

    @Bean("dataSource2")
    @Qualifier("dataSource2")
    public DataSource dataSource2(@Qualifier("dataSourceProperties2") DataSourceProperties dataSourceProperties2) {
        return dataSourceProperties2.initializeDataSourceBuilder().build();
    }

    @Bean("jdbcTemplate2")
    @Qualifier("jdbcTemplate2")
    public NamedParameterJdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource2) {
        return new NamedParameterJdbcTemplate(dataSource2);
    }

}
