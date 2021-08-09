package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.aspects.NoLogging;
import com.example.tw1tt3rServer.configs.NumberConstConfig;
import com.example.tw1tt3rServer.repository.TweetRepository;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TweetService {

    //------------------------------------------------------------------------------------------------------------

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    NumberConstConfig numberConstConfig;

    //------------------------------------------------------------------------------------------------------------

    private Tweet save(Tweet tweet) {
        return (tweetRepository.save(tweet));
    }

    @NoLogging
    public Tweet findById(int id){
        return(tweetRepository.findById(id).get());
    }

    //------------------------------------------------------------------------------------------------------------

    @NoLogging
    public List<Tweet> findByPersonWhoMadeThis(Person person) {
        return (tweetRepository.findByPersonWhoMadeThisOrderByTimestamp(person, numberConstConfig.getReportLimit()));
    }

    @NoLogging
    public List<Tweet> findAllByPersonWhoMadeThisIsNotNullOrderByTimestamp() {
        return (tweetRepository.findAllByPersonWhoMadeThisIsNotNullOrderByTimestamp(numberConstConfig.getReportLimit()));
    }

    @NoLogging
    public List<Tweet> findAllByPersonWhoMadeThisIsNotNullAndPersonIsPublicOrderByTimestamp() {
        return (tweetRepository.findAllByPersonWhoMadeThisIsNotNullAndPersonIsPublicOrderByTimestamp(numberConstConfig.getReportLimit()));
    }

    @NoLogging
    public List<Tweet> findAllByParentTweetOrderByTimestamp(Tweet parentTweet) {
        return (tweetRepository.findAllByParentTweetOrderByTimestamp(parentTweet, numberConstConfig.getReportLimit()));
    }

    public void makeTweet(String text, Tweet parentTweet, Person personWhoMadeThis, Timestamp timestamp,
                          Picture picture) {
        Tweet tweet = new Tweet();
        tweet.setText(text);
        tweet.setParentTweet(parentTweet);
        tweet.setPersonWhoMadeThis(personWhoMadeThis);
        tweet.setTimestamp(timestamp);
        tweet.setPicture(picture);
        save(tweet);
    }

    public Tweet like(Tweet tweet, Person person){
        tweet.getWhoLiked().add(person);
        person.getWhichTweetLiking().add(tweet);
        return(save(tweet));
    }

    public Tweet unLike(Tweet tweet, Person person){
        if(tweet.getWhoLiked().contains(person)){
            tweet.getWhoLiked().remove(person);
            person.getWhichTweetLiking().remove(tweet);
            return(save(tweet));
        }
        return(tweet);
    }

    public Tweet saveTweet(Tweet tweet, Person person){
        tweet.getPeopleWhoSaved().add(person);
        person.getSavedTweets().add(tweet);
        return(save(tweet));
    }

    public Tweet unSaveTweet(Tweet tweet, Person person){
        if(tweet.getPeopleWhoSaved().contains(person)){
            tweet.getPeopleWhoSaved().remove(person);
            person.getSavedTweets().remove(tweet);
            return(save(tweet));
        }
        return(tweet);
    }

    public Tweet report(Tweet tweet, Person person){
        tweet.getWhoReports().add(person);
        person.getTweetsReporting().add(tweet);
        return(save(tweet));
    }
}
