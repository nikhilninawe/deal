package main;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"entities", "controller", "repository", "manager", "executor"})
@EnableJpaRepositories(basePackages = {"repository"})
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages={"entities"})
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return properties;
	}

	@Bean
	public ExecutorService executorService(){
		return Executors.newFixedThreadPool(200);
	}
}
