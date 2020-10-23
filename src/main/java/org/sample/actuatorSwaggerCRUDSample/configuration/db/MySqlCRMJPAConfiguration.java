package org.sample.actuatorSwaggerCRUDSample.configuration.db;


import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableJpaRepositories(basePackages = "org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm",
        entityManagerFactoryRef = "mySqlCRMEntityManagerFactory",
        transactionManagerRef = "mySqlCRMTransactionManager")
public class MySqlCRMJPAConfiguration {

    @Bean
    public DataSource mySqlCRMDataSource(final @Value("${mysql.crm.datasource.driver}") String mysqlCRMDataSourceDriver,
                                         final @Value("${mysql.crm.datasource.url}") String mysqlCRMDataSourceUrl,
                                         final @Value("${mysql.crm.datasource.username}") String mysqlCRMDataSourceUsername,
                                         final @Value("${mysql.crm.datasource.password}") String mysqlCRMDataSourcePassword) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(mysqlCRMDataSourceDriver);
        driverManagerDataSource.setUrl(mysqlCRMDataSourceUrl);
        driverManagerDataSource.setUsername(mysqlCRMDataSourceUsername);
        driverManagerDataSource.setPassword(mysqlCRMDataSourcePassword);
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mySqlCRMEntityManagerFactory(final @Qualifier("mySqlCRMDataSource") DataSource dataSource,
                                                                               final @Value("${mysql.crm.hibernate.hbm2ddl.auto}") String mysqlCrmHibernateHbm2ddlAuto,
                                                                               final @Value("${mysql.crm.hibernate.format_sql}") String mysqlCrmHibernateFormat_sql,
                                                                               final @Value("${mysql.crm.hibernate.show_sql}") String mysqlCrmHibernateShow_sql){
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        lemfb.setJpaVendorAdapter(jpaVendorAdapter);
        lemfb.setDataSource(dataSource);
        lemfb.setPackagesToScan(new String[]{"org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity"});

        lemfb.setJpaPropertyMap(new HashMap<String, String>() {{
            put("hibernate.hbm2ddl.auto", mysqlCrmHibernateHbm2ddlAuto);
            put("hibernate.format_sql", mysqlCrmHibernateFormat_sql);
            put("hibernate.show_sql", mysqlCrmHibernateShow_sql);
        }});
        return lemfb;
    }

    @Bean
    public JpaTransactionManager mySqlCRMTransactionManager(final @Qualifier("mySqlCRMEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
