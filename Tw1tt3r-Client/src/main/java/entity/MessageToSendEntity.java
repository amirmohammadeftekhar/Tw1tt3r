package entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class MessageToSendEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "destinationRoomToSend_ID")
    private RoomEntity destinationRoomToSend;

    @Column
    private Timestamp timestamp;

    @Column
    private String text;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    byte[] picture;
}





















