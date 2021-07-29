package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    @Query(value = "select t from Tweet t where t.personWhoMadeThis.activeState=true and t.whoReports.size < ?2 and t.personWhoMadeThis" +
            ".deleted=false and t.personWhoMadeThis = ?1 order by t.timestamp")
    List<Tweet> findByPersonWhoMadeThisOrderByTimestamp(Person person, int reportLimit);

    @Query(value = "select t from Tweet t where t.personWhoMadeThis.activeState=true and t.whoReports.size < ?1 and t.personWhoMadeThis" +
            ".deleted=false order by t.timestamp")
    List<Tweet> findAllByPersonWhoMadeThisIsNotNullOrderByTimestamp(int reportLimit);

    @Query(value = "select t from Tweet t where t.personWhoMadeThis.activeState=true and t.whoReports.size < ?1 and t.personWhoMadeThis" +
            ".deleted=false and t.personWhoMadeThis.isPrivate=false order by t.timestamp")
    List<Tweet> findAllByPersonWhoMadeThisIsNotNullAndPersonIsPublicOrderByTimestamp(int reportLimit);

    @Query(value = "select t from Tweet t where t.personWhoMadeThis.activeState=true and t.whoReports.size < ?2 and t.personWhoMadeThis" +
            ".deleted=false and t.parentTweet=?1 order by t.timestamp")
    List<Tweet> findAllByParentTweetOrderByTimestamp(Tweet parentTweet, int reportLimit);
}
