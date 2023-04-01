package lk.ijse.orm.hostelSystem.util;

import lk.ijse.orm.hostelSystem.entity.Reservation;
import lk.ijse.orm.hostelSystem.entity.Room;
import lk.ijse.orm.hostelSystem.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

        private static FactoryConfiguration factoryConfiguration;
        private final SessionFactory sessionFactory;

        private FactoryConfiguration() {

            Configuration config = new Configuration()
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Reservation.class);
            sessionFactory = config.buildSessionFactory();
        }

        public static FactoryConfiguration getInstance() {
            return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
        }

        public Session getSession() {
            return sessionFactory.openSession();
        }
}
