package com.example.tw1tt3rServer.repository.entity;

import entities.enums.LastSeenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Person {

    public Person(){

    }

    //------------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String firstname;

    @Column
    private String lastName;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String token;

    @Column
    private String emailAddress;

    @Column
    private boolean activeState;

    @Column
    private Timestamp lastSeen;

    @Column
    private boolean isPrivate;

    @Column
    boolean deleted;

    @Column
    private boolean toShowEmail;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    Picture picture;

    @Enumerated(EnumType.STRING)
    private LastSeenType lastSeenType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "peopleWhoSaved")
    private Set<Tweet> savedTweets = new HashSet<Tweet>();

    @ManyToOne
    @JoinColumn(name = "savedMessageRoom_id")
    private Room savedMessageRoom;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourcePerson")
    private Set<Category> ownedCategories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_category",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> includedCategories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_unreadmessages",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Set<Message> readMessages = new HashSet<Message>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_room",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<Room>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "whoReports")
    private Set<Tweet> tweetsReporting = new HashSet<Tweet>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "whoLiked")
    private Set<Tweet> whichTweetLiking = new HashSet<Tweet>();

    public String getFullIdentity() {
        if (lastSeenType == LastSeenType.EVERYBODY) {
            return ("Name: " + firstname + " " + lastName + "\nusername: " + userName + "\nLast seen: " + lastSeen +
                    "\nEmail: " + (toShowEmail ? emailAddress : ": )"));
        }
        return ("Name: " + firstname + " " + lastName + "\nusername: " + userName + "\nLast seen: recently" +
                "\nEmail: " + (toShowEmail ? emailAddress : ": )"));
    }

    //------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return ("Username: " + userName + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Person))
            return false;

        Person other = (Person) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
