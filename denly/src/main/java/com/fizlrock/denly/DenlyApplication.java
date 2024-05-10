package com.fizlrock.denly;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.EntityManager;

@SpringBootApplication
public class DenlyApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(DenlyApplication.class, args);
		var dataSource = context.getBean("dataSource");
		System.out.println(dataSource);

		var em = context.getBean(EntityManager.class);

		Session session = (Session) em.getDelegate();
		var prop = session.getSessionFactory().getProperties();
		System.out.println(prop);
	}

}
