package bootstrap;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionRunner {

    private static SessionFactory sessionFactory;

    private SessionRunner() {

    }

    public static SessionFactory initSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }

}
