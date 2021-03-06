package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Picture;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import dtos.TweetDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class TweetServiceServerController extends AbstractServerController {
    @PostMapping("api/tweetservice/maketweet")
    public ResponseEntity<Void> makeTweet(@RequestParam String text, @RequestParam int parentTweetId, @RequestParam int currentPersonId, @RequestParam int pictureId){
        Person currentPerson = personService.findById(currentPersonId);
        Tweet parentTweet = null;
        if(parentTweetId>0) parentTweet = tweetService.findById(parentTweetId);
        Picture picture = pictureService.findById(pictureId);
        tweetService.makeTweet(text, parentTweet, currentPerson, getTimeStamp(), picture);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @GetMapping("api/tweetservice/gettweet")
    public TweetDto getTweet(@RequestParam int tweetId){
        Tweet tweet = tweetService.findById(tweetId);
        TweetDto tweetDto = ModelMapperInstance.getModelMapper().map(tweet, TweetDto.class);
        return(tweetDto);
    }

    @PostMapping("api/tweetservice/report")
    public ResponseEntity<Void> report(@RequestParam int tweetId, @RequestParam int currentPersonId){
        Person currentPerson = personService.findById(currentPersonId);
        Tweet tweet = tweetService.findById(tweetId);
        tweetService.report(tweet, currentPerson);
        return(new ResponseEntity<Void>(HttpStatus.OK));
    }
}















