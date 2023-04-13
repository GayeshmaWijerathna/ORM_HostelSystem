package lk.ijse.orm.hostelSystem.dao.custom;

import lk.ijse.orm.hostelSystem.dao.CrudDAO;
import lk.ijse.orm.hostelSystem.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<Login, String> {

    ArrayList<Login> getAllUsers() throws SQLException, ClassNotFoundException;
}
