package com.example.springboot07;

import com.example.springboot07.model.Account;
import com.example.springboot07.model.Role;
import com.example.springboot07.repo.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final AccountRepository accountRepository) {
		return (args) -> {
			// Default accounts
            accountRepository.save(new Account("rwibawa", "Ch@ng3M3!", Role.USER));
            accountRepository.save(new Account("admin", "admin", Role.ADMIN));
            accountRepository.save(new Account("actuator", "management", Role.ACTUATOR));
        };
	}
}
