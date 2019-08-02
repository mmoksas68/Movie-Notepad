package com.obss.userProject;

import com.obss.userProject.classes.Role;
import com.obss.userProject.classes.requestClasses.RoleName;
import com.obss.userProject.dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class UserProjectApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(UserProjectApplication.class, args);
	}

}
