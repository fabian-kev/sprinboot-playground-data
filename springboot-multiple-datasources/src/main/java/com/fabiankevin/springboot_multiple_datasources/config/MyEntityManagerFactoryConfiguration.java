package com.fabiankevin.springboot_multiple_datasources.config;

import com.fabiankevin.springboot_multiple_datasources.persistence.first.WeatherEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class MyEntityManagerFactoryConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(JpaProperties jpaProperties, EntityManagerFactoryBuilder builder, DataSource primaryDataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.fabiankevin.s");
        factory.setDataSource(primaryDataSource);

        return builder.dataSource(primaryDataSource)
                .properties(jpaProperties.getProperties())
                .packages(WeatherEntity.class).persistenceUnit("primary").build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    //    the below methods are not yet working and to be figured out
    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(jpaProperties);

        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
    }

    private JpaVendorAdapter createJpaVendorAdapter(JpaProperties firstJpaProperties) {
        // ... map JPA properties as needed
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform(firstJpaProperties.getDatabasePlatform());
        hibernateJpaVendorAdapter.setGenerateDdl(firstJpaProperties.isGenerateDdl());
        hibernateJpaVendorAdapter.setShowSql(firstJpaProperties.isShowSql());
//        hibernateJpaVendorAdapter.set
        return hibernateJpaVendorAdapter;
    }


}