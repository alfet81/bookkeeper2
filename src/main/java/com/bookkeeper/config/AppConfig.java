package com.bookkeeper.config;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableAutoConfiguration
public class AppConfig {

  @Bean
  public DataSource dataSource() {

    var builder = new EmbeddedDatabaseBuilder();
    return builder.setType(H2).build();
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {

    var vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(false);

    var factory = new LocalContainerEntityManagerFactoryBean();

    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.bookkeeper.domain");
    factory.setDataSource(dataSource());
    factory.afterPropertiesSet();

    return factory.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    var txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory());
    return txManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new StandardPasswordEncoder();
  }
}
