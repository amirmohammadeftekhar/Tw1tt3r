package dtos;

import java.sql.Timestamp;

public class DtoUtility {
    public static void makeRoomHealthy(RoomDto roomDto){
        Timestamp now = new Timestamp(System.currentTimeMillis());
//        roomDto.getMessages().removeIf(messageDto -> messageDto.getTimestamp().before(now));
        roomDto.getMessages().removeIf(messageDto -> messageDto.getTimestamp().compareTo(now)>0);
    }
    public static void makePersonHealthy(PersonDto personDto){
        for(RoomDto roomDto:personDto.getRooms()){
            makeRoomHealthy(roomDto);
        }
    }
}
