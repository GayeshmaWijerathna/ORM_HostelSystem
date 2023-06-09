package lk.ijse.orm.hostelSystem.bo;

import lk.ijse.orm.hostelSystem.bo.custom.impl.ReserveBOImpl;
import lk.ijse.orm.hostelSystem.bo.custom.impl.RoomBOImpl;
import lk.ijse.orm.hostelSystem.bo.custom.impl.StudentBOImpl;
import lk.ijse.orm.hostelSystem.bo.custom.impl.UserBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case STUDENT:
                return new StudentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVE:
                return new ReserveBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        STUDENT, ROOM, RESERVE, USER
    }
}
