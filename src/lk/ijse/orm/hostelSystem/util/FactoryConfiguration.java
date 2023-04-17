package lk.ijse.orm.hostelSystem.util;

import lk.ijse.orm.hostelSystem.entity.Loging;
import lk.ijse.orm.hostelSystem.entity.Reservation;
import lk.ijse.orm.hostelSystem.entity.Room;
import lk.ijse.orm.hostelSystem.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;

    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        sessionFactory = new Configuration()
                .mergeProperties(getProperties())
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Loging.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader
                    .getSystemClassLoader()
                    .getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            System.out.println("Property file not found!");
            e.printStackTrace();
        }
        return properties;
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration()
                : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

}
