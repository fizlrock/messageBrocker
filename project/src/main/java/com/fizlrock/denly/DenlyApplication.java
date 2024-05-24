package com.fizlrock.denly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.fizlrock.denly.Domain.User;

import jakarta.persistence.EntityManagerFactory;

@SpringBootApplication
public class DenlyApplication {

	static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(DenlyApplication.class, args);


		createUser();
	}

	@Transactional
	static void createUser() {

		var emf = context.getBean(EntityManagerFactory.class);
		var em = emf.createEntityManager();

		em.getTransaction().begin();

		// var query = em.createNativeQuery("select * from users", User.class);
		var u = new User("fizlrock","123123123");

		em.persist(u);
		em.getTransaction().commit();
	}

}
