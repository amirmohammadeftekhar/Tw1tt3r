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
public class Tweet {

    //------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String text;

    @ManyToOne
    private Tweet parentTweet;

    @ManyToOne
    private Person personWhoMadeThis;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tweet_personwholiked",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> whoLiked = new HashSet<Person>();

    @Column
    private Timestamp timestamp;

    @ManyToOne
    private Person rootPerson;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tweet_personwhoreports",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> whoReports = new HashSet<Person>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_savedtweets",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> peopleWhoSaved = new HashSet<Person>();

    @Override
    public String toString() {
        return (personWhoMadeThis.getUserName() + " Wrote this: " + text + "\n" + "Id: " + Integer.toString(Id) + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tweet))
            return false;

        Tweet other = (Tweet) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
