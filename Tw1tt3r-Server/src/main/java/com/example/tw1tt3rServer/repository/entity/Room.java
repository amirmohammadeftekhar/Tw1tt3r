package com.example.tw1tt3rServer.repository.entity;

import com.example.tw1tt3rServer.repository.entity.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    private Set<Person> members = new HashSet<Person>();

    @OrderBy("timestamp")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationRoom")
    private Set<Message> messages = new HashSet<Message>();

    @Enumerated(EnumType.STRING)
    private RoomType roomType;
//    private boolean pv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Room))
            return false;

        Room other = (Room) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Timestamp getLastMessageTimeStamp(){
        Timestamp timestamp = new Timestamp(0);
        for(Message message:messages){
            if(timestamp.compareTo(message.getTimestamp()) < 0){
                timestamp = message.getTimestamp();
            }
        }
        return(timestamp);
    }
}
