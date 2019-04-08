package io.example.room;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class RoomRepository {

  private static final Map<String, Room> rooms = Collections.synchronizedMap(new HashMap<String, Room>());

  public static Room getOrCreate(String roomId) {
    Room room = RoomRepository.rooms.get(roomId);
    if (room == null) {
      room = Room.of(roomId);
      RoomRepository.rooms.putIfAbsent(roomId, room);
    }
    return room;
  }

  public static Room withSession(Session session) {
    String roomId = (String) session.getUserProperties().get("roomId");
    return RoomRepository.rooms.get(roomId);
  }

  public static void delete(String roomId) {
    rooms.remove(roomId);
  }

  public static String listRooms() {
    return rooms.toString();
  }
}