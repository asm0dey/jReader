package com.github.asm0dey.server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration config = new Configuration();
			config.configure();
			ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
			srBuilder.applySettings( config.getProperties() );
			ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
			sessionFactory = config.buildSessionFactory( serviceRegistry );
		} catch ( Throwable ex ) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println( "Initial SessionFactory creation failed." + ex );
			throw new ExceptionInInitializerError( ex );
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
