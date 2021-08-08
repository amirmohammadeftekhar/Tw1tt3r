package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import dtos.TweetDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import utility.TimeLineParent;
import utility.enums.TimeLineParents;

import java.util.LinkedList;
import java.util.List;

@RestController
public class TimeLineServerController extends AbstractServerController{

    private boolean toShow(Tweet tweet, int currentPersonId) {
        Person currentPerson = personService.findById(currentPersonId);
        if (tweet.getPersonWhoMadeThis().equals(currentPerson)) {
            return (true);
        }
        if (actionService.isBlockState(currentPerson, tweet.getPersonWhoMadeThis())) {
            return (false);
        }
        if (actionService.getFollowingsPersons(currentPerson).contains(tweet.getPersonWhoMadeThis())) {
            return (true);
        }
        for (Person person : actionService.getFollowingsPersons(currentPerson)) {
            if (tweet.getWhoLiked().contains(person)) {
                return (true);
            }
        }
        return (false);
    }

    @PostMapping("api/timeline/gettweetlist")
    public List<TweetDto> getTweetList(@RequestParam int currentPersonId, @RequestBody TimeLineParent timeLineParent){
        List<Tweet> tweetList;
        if (timeLineParent.getTimeLineParents() == TimeLineParents.HEAD) {
            tweetList = tweetService.findAllByPersonWhoMadeThisIsNotNullOrderByTimestamp();
        }
        else if(timeLineParent.getTimeLineParents() == TimeLineParents.TWEET){
            Tweet parentTweet = tweetService.findById(timeLineParent.getTweetId());
            tweetList = tweetService.findAllByParentTweetOrderByTimestamp(parentTweet);
        }
        else{
            Person person = personService.findById(timeLineParent.getPersonId());
            tweetList = tweetService.findByPersonWhoMadeThis(person);
        }
        tweetList.removeIf(tweet -> actionService.isSourceMuting(personService.findById(currentPersonId), tweet.getPersonWhoMadeThis()));
        tweetList.removeIf(tweet -> !toShow(tweet, currentPersonId));
        List<TweetDto> newList = new LinkedList<TweetDto>();
        for(Tweet tweet:tweetList){
            newList.add(ModelMapperInstance.getModelMapper().map(tweet, TweetDto.class));
        }
        return(newList);
    }
}
