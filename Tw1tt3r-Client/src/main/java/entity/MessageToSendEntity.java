package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class MessageToSendEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private int RoomId;

    @Column
    private String text;

    @Column
    private String fileName;

    @Column
    private int personId;

    @Column
    private Timestamp timestamp;

}





















