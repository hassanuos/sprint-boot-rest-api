package com.journal.newmongoproject;

import com.mongodb.client.MongoClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
public class NewmongoprojectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NewmongoprojectApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		System.out.println(Arrays.toString(environment.getActiveProfiles()));
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
