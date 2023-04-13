package lk.ijse.orm.hostelSystem.dao;

import lk.ijse.orm.hostelSystem.dao.custom.Impl.ReserveDAOImpl;
import lk.ijse.orm.hostelSystem.dao.custom.Impl.RoomDAOImpl;
import lk.ijse.orm.hostelSystem.dao.custom.Impl.StudentDAOImpl;
import lk.ijse.orm.hostelSystem.dao.custom.Impl.UserDAOImpl;

public class DAOFactory {

        private static DAOFactory daoFactory;

        private DAOFactory() {
        }

        public static DAOFactory getDaoFactory() {
            return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
        }

        public SuperDAO getDAO(DAOTypes types) {
            switch (types) {
                case STUDENT:
                    return new StudentDAOImpl();
                case ROOM:
                    return new RoomDAOImpl();
                case RESERVE:
                    return new ReserveDAOImpl();
                case USER:
                    return new UserDAOImpl();
                default:
                    return null;
            }
        }

        public enum DAOTypes {
            STUDENT, ROOM, RESERVE,USER
        }

}
