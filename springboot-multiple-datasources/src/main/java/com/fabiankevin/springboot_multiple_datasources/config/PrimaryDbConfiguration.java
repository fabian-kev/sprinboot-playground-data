package com.fabiankevin.springboot_multiple_datasources.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.fabiankevin.springboot_multiple_datasources.persistence.primary"},
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager")
public class PrimaryDbConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource primaryDataSource(DataSourceProperties primaryDataSourceProperties) {
        return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.fabiankevin.springboot_multiple_datasources.persistence.primary");
        em.setPersistenceUnitName("primaryPersistenceUnit");

        // Specify the JPA vendor adapter (Hibernate)
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Additional JPA properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop"); // Auto-create tables
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect"); // Auto-detected by Spring Boot
        properties.put("hibernate.show_sql", "false"); // Default: SQL logging disabled
        properties.put("hibernate.format_sql", "false"); // Default: SQL formatting disabled
        properties.put("hibernate.use_sql_comments", "false"); // Default: SQL comments disabled
        properties.put("hibernate.jdbc.batch_size", "30"); // Default batch size
        properties.put("hibernate.order_inserts", "true"); // Default: order inserts for batching
        properties.put("hibernate.order_updates", "true"); // Default: order updates for batching
        properties.put("hibernate.generate_statistics", "false"); // Default: statistics disabled
        properties.put("hibernate.cache.use_second_level_cache", "false"); // Default: second-level cache disabled
        properties.put("hibernate.cache.use_query_cache", "false"); // Default: query cache disabled
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}