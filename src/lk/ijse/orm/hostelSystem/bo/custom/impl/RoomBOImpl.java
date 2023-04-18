package lk.ijse.orm.hostelSystem.bo.custom.impl;

import lk.ijse.orm.hostelSystem.bo.custom.RoomBO;
import lk.ijse.orm.hostelSystem.dao.DAOFactory;
import lk.ijse.orm.hostelSystem.dao.custom.RoomDAO;
import lk.ijse.orm.hostelSystem.dto.RoomDTO;
import lk.ijse.orm.hostelSystem.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBOImpl implements RoomBO {

    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public ArrayList<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> allRoom = new ArrayList<>();
        for (Room room : all) {
            allRoom.add(new RoomDTO(room.getRoom_id(), room.getType(), room.getKey_money(), room.getQty()));
        }
        return allRoom;
    }

    @Override
    public ArrayList<RoomDTO> searchAllRooms(String id) throws SQLException, ClassNotFoundException {
        Room all = roomDAO.search(id);
        ArrayList<RoomDTO> allSRoom = new ArrayList<>();

        allSRoom.add(new RoomDTO(all.getRoom_id(), all.getType(), all.getKey_money(), all.getQty()));

        return allSRoom;
    }

    @Override
    public boolean saveRooms(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.save(new Room(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty()));

    }

    @Override
    public boolean updateRooms(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty()));

    }

    @Override
    public boolean deleteRooms(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(id);

    }

    @Override
    public boolean existRoomsID(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.exist(id);

    }
}
