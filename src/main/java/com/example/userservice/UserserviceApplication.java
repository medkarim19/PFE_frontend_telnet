package com.example.userservice;

import com.example.userservice.domain.User;
import com.example.userservice.service.Userservice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder () {
		return new  BCryptPasswordEncoder ();
	}
	
	@Bean
	CommandLineRunner run ( Userservice userservice) {
		return args -> {
			userservice.saveUser(new User(null,"foulen1","foulen1","foulen1",null,"mohamedkarimbezine6@gmail.com",null,new ArrayList<>(),null,null,null,null,null,null,null));
			//userservice.saveRole(new Role(null,"Admin",null));
			//userservice.saveRole(new Role(null,"Chef de projet",null));
			//userservice.saveRole(new Role(null,"Propriétaire du risque",null));
			//userservice.saveRole(new Role(null,"CHEF_PROJET",null));
			//userservice.saveRole(new Role(null,"CREATEUR_RISQUE",null));
			//userservice.saveRole(new Role(null,"ROLE_ADMIN", null));
			//userservice.deleteUser(Long.valueOf(60));
			//userservice.deleteUser(Long.valueOf(4));
			//userservice.deleteUser(Long.valueOf(5));
			//userservice.deleteUser(Long.valueOf(58));
			//userservice.saveUser(new User(null,"hajer80","hajer80","jamm80",null,"hajerjammoussi810@gmail.com",null,new ArrayList<>(),null,null,null,null));
			//userservice.saveUser(new User(null,"karim","karim","1234",new ArrayList<>()));
			//userservice.saveUser(new User(null,"baya","jamm2","1234",new ArrayList<>()));
			//userservice.saveUser(new User(null,"zaineb","jamm3","1234",new ArrayList<>()));
			//userservice.saveUser(new User(null,"mariem","hadj","1234",new ArrayList<>()));
			//userservice.saveUser(new User(null,"samir","samir","1234",new ArrayList<>()));
			//Map<String, Boolean> user = userservice.deleteUser("hadj");
			//User user6=new User(null,"issam","jamm7","9999",new ArrayList<>());
			//userservice.updateUser(user6,"jamm3");
			//userservice.updateUser(user6,"karim");
			//userservice.saveRole(new Role(null,"ROLE bleda"));
			//Role role5=new Role(null,"ROLE KDIM");
			//userservice.updateRole(role5,"ROLE bleda");
			//User user12=new User(null,"ghada","ghada","9999",new ArrayList<>());
			//User jamm2 = userservice.updateUser(user12, "jamm2");
			//userservice.saveUser(new User(null,"karim15","karim15","karim15",null,"mohamedkarimbezine6@gmail.com",null,new ArrayList<>(),null,null,null,null));
			//userservice.deleteUser(Long.valueOf(76));
			//userservice.saveUser(new User(null,"karim","karim","karim",null,"mohamedkarimbezine6@gmail.com",null,new ArrayList<>(),null,null,null,null));


			//userservice.saveUser(new User(null,"hajer400","hajer400","hajer400",null,"mohamedkarimbezine6@gmail.com",null,new ArrayList<>(),null,null,null,null));
				//userservice.deleteUser(Long.valueOf(28));
				//userservice.deleteUser(Long.valueOf(23));


		};
		
	}

}
