package net.petrikainulainen.spring.datajpa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.jolbox.bonecp.BoneCPDataSource;

public abstract class AbstractTransactionalPersistenceContext {

	protected static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	protected static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	protected static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	protected static final String PROPERTY_PACKAGES_TO_SCAN = "net.petrikainulainen.spring.datajpa.todo.model";
	
	@Autowired
	protected Environment environment;

	public AbstractTransactionalPersistenceContext() {
		super();
	}

	@Bean
	public DataSource dataSource() {
	    BoneCPDataSource dataSource = new BoneCPDataSource();
	
	    dataSource.setDriverClass(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
	    dataSource.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
	    dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
	    dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
	
	    return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	
	    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
	
	    return transactionManager;
	}
	
	public abstract LocalContainerEntityManagerFactoryBean entityManagerFactory();

}