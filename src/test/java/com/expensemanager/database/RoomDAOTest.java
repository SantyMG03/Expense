package com.expensemanager.database;

import com.expensemanager.model.Room;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomDAOTest {

    @BeforeAll
    static void setupDatabase() {
        DataBaseManager.connect();
        RoomDAO.createTable();
    }

    @BeforeEach
    void cleanDatabase() {
        // Limpiar la tabla antes de cada prueba
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM rooms")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Error al limpiar la base de datos: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testInsertRoom() {
        int roomId = RoomDAO.insertRoom("Fiesta de cumpleaños");
        assert(roomId > 0);

        Room room = RoomDAO.getRoomById(roomId);
        assert(room != null);
        assert(room.getName().equals("Fiesta de cumpleaños"));
    }

    @Test
    @Order(2)
    void testUpdateRoom() {
        int roomId = RoomDAO.insertRoom("Reunión familiar");
        boolean updated = RoomDAO.updateRoom(roomId, "Reunión de primos");

        assert(updated);
        Room room = RoomDAO.getRoomById(roomId);
        assert(room.getName().equals("Reunión de primos"));
    }

    @Test
    @Order(3)
    void testGetAllRooms() {
        List<Room> rooms = RoomDAO.getAllRooms();
        assert(rooms.isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteRoom() {
        int roomId = RoomDAO.insertRoom("Viaje a la playa");
        boolean deleted = RoomDAO.deleteRoom(roomId);

        assert(deleted);
        assert(RoomDAO.getRoomById(roomId) == null);
    }
}
