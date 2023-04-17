package lk.ijse.orm.hostelSystem.dao.custom;

import lk.ijse.orm.hostelSystem.dao.CrudDAO;
import lk.ijse.orm.hostelSystem.entity.Loging;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<Loging, String> {

    ArrayList<Loging> getAllUsers() throws SQLException, ClassNotFoundException;
}
