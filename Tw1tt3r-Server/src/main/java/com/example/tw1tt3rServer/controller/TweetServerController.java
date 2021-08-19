package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetServerController extends AbstractServerController{
    @PostMapping("api/tweet/likebuttonaction")
    public ResponseEntity<Void> likeButtonAction(@RequestParam int tweetId, @RequestParam int currentPersonId){
        Person currentPerson = personService.findById(currentPersonId);
        Tweet tweet = tweetService.findById(tweetId);
        if(tweet.getWhoLiked().contains(currentPerson)){
            tweet = tweetService.unLike(tweet, currentPerson);
        }
        else{
            tweet = tweetService.like(tweet, currentPerson);
        }
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @PostMapping("api/tweet/savetweetbuttonaction")
    public ResponseEntity<Void> saveTweetButtonAction(@RequestParam int tweetId, @RequestParam int currentPersonId){
        Person currentPerson = personService.findById(currentPersonId);
        Tweet tweet = tweetService.findById(tweetId);
        roomService.sendMessage(tweet.getText(), currentPerson, currentPerson.getSavedMessageRoom(), tweet.getPicture(), 0);
        tweet = tweetService.saveTweet(tweet, currentPerson);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

}





















