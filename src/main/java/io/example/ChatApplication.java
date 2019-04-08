package io.example;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import io.example.room.Room;
import io.example.room.RoomRepository;

/**
 * Configures a Websocket endpoint.
 * 
 * @author Anderson Carvalho
 */
@ApplicationScoped
@ServerEndpoint("/chat/{roomId}/{userId}")
public class ChatApplication {

  @OnMessage
  public void onMessage(String message, Session session) {
      RoomRepository.withSession(session).broadcast(message);
  }

  @OnOpen
  public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("userId") String userId) {
    session.setMaxIdleTimeout(1000 * 60 * 15); // 15 minutes
    
    // Assign the session to the user
    session.getUserProperties().putIfAbsent("userId", userId);

    Room room = RoomRepository.getOrCreate(roomId);

    // Join the session in the room - Every participant in an room can comunicate between them
    room.join(session);
  }

  @OnClose
  public void onClose(Session session, @PathParam("roomId") String roomId, @PathParam("userId") String userId) {
    Room room = RoomRepository.withSession(session);
    room.leave(session);
    RoomRepository.delete(roomId);
  }

  @OnError
  public void onError(Session session, Throwable error) {
    System.out.println("Error on chat room xxx...");
  }
}
