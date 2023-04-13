package lk.ijse.orm.hostelSystem.dao.custom;

import lk.ijse.orm.hostelSystem.dao.CrudDAO;
import lk.ijse.orm.hostelSystem.entity.Reservation;

import java.sql.SQLException;

public interface ReserveDAO extends CrudDAO<Reservation, String> {

    String generateNewId() throws SQLException, ClassNotFoundException;

    boolean existStudent(String id) throws SQLException, ClassNotFoundException;
}
