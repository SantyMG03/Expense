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
        int roomId = RoomDAO.insertRoom("Fiesta de cumpleaños");
        assertThat(roomId).isGreaterThan(0);

        Room room = RoomDAO.getRoomById(roomId);
        assertThat(room).isNotNull();
        assertThat(room.getName()).isEqualTo("Fiesta de cumpleaños");
    }

    @Test
    @Order(2)
    void testUpdateRoom() {
        int roomId = RoomDAO.insertRoom("Reunión familiar");
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
        int roomId = RoomDAO.insertRoom("Viaje a la playa");
        boolean deleted = RoomDAO.deleteRoom(roomId);

        assertThat(deleted).isTrue();
        assertThat(RoomDAO.getRoomById(roomId)).isNull();
    }
}
