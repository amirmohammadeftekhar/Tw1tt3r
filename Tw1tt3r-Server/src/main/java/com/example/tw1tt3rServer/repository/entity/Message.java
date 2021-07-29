package com.example.tw1tt3rServer.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Person sourcePerson;

    @ManyToOne
    @JoinColumn(name = "destinationRoom_ID")
    private Room destinationRoom;

    @Column
    private Timestamp timestamp;

    @Column
    private String text;

    @Column
    private boolean isNotified;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @ManyToMany(mappedBy = "readMessages", fetch = FetchType.LAZY)
    private Set<Person> whoHasRead = new HashSet<Person>();

    @Override
    public String toString() {
        return (sourcePerson.getUserName() + ":\n" + text + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Message))
            return false;

        Message other = (Message) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
