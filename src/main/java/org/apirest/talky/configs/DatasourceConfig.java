package org.apirest.talky.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


//@Configuration
public class DatasourceConfig {

    //@Bean
    public DataSource dataSource() {
        final var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:talkydb");
        dataSource.setUsername("sa");
        dataSource.setPassword("Password");

        return dataSource;
    }
}
