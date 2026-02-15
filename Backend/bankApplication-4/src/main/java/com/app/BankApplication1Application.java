package com.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.entity.Role;
import com.app.entity.User;
import com.app.repo.UserRepository;

@SpringBootApplication
public class BankApplication1Application {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication1Application.class, args);
	}
//	@Bean
//	public CommandLineRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
//	    return args -> {
//	        repo.save(new User("ekta", "Ekta Sharma", encoder.encode("ekta123"), Role.CLERK));
//	        repo.save(new User( "raj", "Raj Kumar", encoder.encode("raj123"), Role.MANAGER));
//	    };
//	}

	
}
