package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class RoomEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String name;

    @Column
    private int roomId;


    @OrderBy("timestamp")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationRoom")
    private Set<MessageEntity> messages = new HashSet<MessageEntity>();

    @OrderBy("timestamp")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationRoomToSend")
    private Set<MessageToSendEntity> messagesToSend = new HashSet<MessageToSendEntity>();

}




















