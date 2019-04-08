package io.example.room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public class Room {

  private final String id;
  private List<Session> sessions = new ArrayList<>();

  private Room(String id) {
    this.id = id;
  }

  public static Room of(String id) {
    return new Room(id);
  }

  public synchronized void join(Session session) {
    System.out.println("User ID: " + session.getUserProperties().get("userId") + " join the room: " + id);
    sessions.add(session);
  }

  public synchronized void leave(Session session) {
    System.out.println("User ID: " + session.getUserProperties().get("userId") + " leave the room: " + id);
    sessions.remove(session);
  }

  public synchronized void broadcast(String message) {
    sessions.parallelStream().filter(Session::isOpen).forEach(session -> {
      try {
        session.getBasicRemote().sendObject(message);
      } catch (IOException | EncodeException e) {
        e.printStackTrace();
      }
    });
  }

  public String getId() {
    return this.id;
  }
}