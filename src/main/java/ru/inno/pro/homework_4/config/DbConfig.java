package ru.inno.pro.homework_4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

  @Bean
  HikariConfig config() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/testdb");
    hikariConfig.setUsername("postgres");
    hikariConfig.setPassword("1");
    hikariConfig.setSchema("test");
    return hikariConfig;
  }

  @Bean
  HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  Connection connection(HikariDataSource dataSource) throws SQLException {
    return dataSource.getConnection();
  }
}
