package com.hub.imei;

import com.hub.imei.entity.AppUser;
import com.hub.imei.entity.Role;
import com.hub.imei.service.UserService;
import com.hub.imei.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ImeiSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImeiSystemApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService){
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_USER"));

			roleService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new AppUser(null, "Utku", "Araal", "5379965627", "utkuaraal1@gmail.com", "civcivciv",  new ArrayList<>(), true, false, ""));


			userService.addRoleToUser("utkuaraal1@gmail.com", "ROLE_ADMIN");




		};
	}

}
