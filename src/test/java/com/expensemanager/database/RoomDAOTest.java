package com.expensemanager.database;

import com.expensemanager.model.Room;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomDAOTest {

    @BeforeAll
    static void setupDatabase() {
        DataBaseManager.connect();
        RoomDAO.createTable();
    }

    @Test
    @Order(1)
    void testInsertRoom() {
        RoomDAO.insertRoom("Fiesta de cumpleaños");
        int roomId = -1;
        List<Room> rooms = RoomDAO.getAllRooms();
        for (Room r : rooms) {
            if (r.getName().equals("Fiesta de cumpleaños")) {
                roomId = r.getId();
            }
        }
        assertThat(roomId).isGreaterThan(0);

        Room room = RoomDAO.getRoomById(roomId);
        assertThat(room).isNotNull();
        assertThat(room.getName()).isEqualTo("Fiesta de cumpleaños");
    }

    @Test
    @Order(2)
    void testUpdateRoom() {
        RoomDAO.insertRoom("Reunión familiar");
        int roomId = -1;
        List<Room> rooms = RoomDAO.getAllRooms();
        for (Room r : rooms) {
            if (r.getName().equals("Reunión familiar")) {
                roomId = r.getId();
            }
        }
        boolean updated = RoomDAO.updateRoom(roomId, "Reunión de primos");

        assertThat(updated).isTrue();
        Room room = RoomDAO.getRoomById(roomId);
        assertThat(room.getName()).isEqualTo("Reunión de primos");
    }

    @Test
    @Order(3)
    void testGetAllRooms() {
        List<Room> rooms = RoomDAO.getAllRooms();
        assertThat(rooms).isNotEmpty();
    }

    @Test
    @Order(4)
    void testDeleteRoom() {
        RoomDAO.insertRoom("Viaje a la playa");
        int roomId = -1;
        List<Room> rooms = RoomDAO.getAllRooms();
        for (Room r : rooms) {
            if (r.getName().equals("Viaje a la playa")) {
                roomId = r.getId();
            }
        }
        boolean deleted = RoomDAO.deleteRoom(roomId);

        assertThat(deleted).isTrue();
        assertThat(RoomDAO.getRoomById(roomId)).isNull();
    }
}
