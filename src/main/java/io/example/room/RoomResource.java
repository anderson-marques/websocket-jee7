package io.example.room;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("rooms")
public class RoomResource {

  @GET
  public String listResources() {
    return "list resources: " + RoomRepository.listRooms();
  }
}