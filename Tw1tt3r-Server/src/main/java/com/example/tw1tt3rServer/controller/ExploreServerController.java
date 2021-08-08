package com.example.tw1tt3rServer.controller;

import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.repository.entity.Tweet;
import dtos.PersonDto;
import dtos.TweetDto;
import dtos.TweetListDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;
import utility.TimeLineParent;
import utility.enums.TimeLineParents;
import web.BaseResponse;
import web.ResponseHeader;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RestController
public class ExploreServerController extends AbstractServerController{

    @PostMapping(value = "api/explore/searchbuttonaction")
    public BaseResponse searchButtonAction(@RequestParam String userName, @RequestParam int currentPersonId){
        Person person = personService.findPersonByUserName(userName);
        Person currentPerson = personService.findById(currentPersonId);
        if(person != null && !actionService.isBlockState(currentPerson, person)){
            return(new BaseResponse(ResponseHeader.OK, (PersonDto) ModelMapperInstance.getModelMapper().map(person, PersonDto.class)));
        }
        return(new BaseResponse(ResponseHeader.NOT_ALLOWED, null));
    }

    private boolean toShow(Tweet tweet, Person currentPerson) {
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

    private Random random = new Random();

    private Tweet chooseTweetFromListRandomly(List<Tweet> tweetList) {
        int sm = 0;
        for (Tweet tweet : tweetList) {
            sm += tweet.getWhoLiked().size() + 1;
        }
        int rnd = random.nextInt(sm);
        for (Tweet tweet : tweetList) {
            rnd -= tweet.getWhoLiked().size() + 1;
            if (rnd < 0) {
                return (tweet);
            }
        }
        return (null);
    }

    @PostMapping(value = "api/explore/gettweetlist")
    public BaseResponse getTweetList(@RequestParam int currentPersonId, @RequestBody TimeLineParent timeLineParent){
        List<Tweet> tweetList;
        Person currentPerson = personService.findById(currentPersonId);
        if (timeLineParent.getTimeLineParents() == TimeLineParents.HEAD) {
            tweetList = new LinkedList<Tweet>();
            List<Tweet> allTweets = tweetService.findAllByPersonWhoMadeThisIsNotNullAndPersonIsPublicOrderByTimestamp();
            allTweets.removeIf(tweet -> actionService.isSourceMuting(currentPerson, tweet.getPersonWhoMadeThis()));
            allTweets.removeIf(tweet -> actionService.isBlockState(currentPerson, tweet.getPersonWhoMadeThis()));
            while (!allTweets.isEmpty()) {
                Tweet tweet = chooseTweetFromListRandomly(allTweets);
                tweetList.add(tweet);
                allTweets.remove(tweet);
            }

        }
        else if(timeLineParent.getTimeLineParents() == TimeLineParents.TWEET){
            Tweet parentTweet = tweetService.findById(timeLineParent.getTweetId());
            System.out.println(parentTweet);
            tweetList = tweetService.findAllByParentTweetOrderByTimestamp(parentTweet);
        }
        else{
            Person person = personService.findById(timeLineParent.getPersonId());
            tweetList = tweetService.findByPersonWhoMadeThis(person);
        }
        tweetList.removeIf(tweet -> actionService.isSourceMuting(currentPerson, tweet.getPersonWhoMadeThis()));
        tweetList.removeIf(tweet -> !toShow(tweet, currentPerson));
        List<TweetDto> newList = new LinkedList<TweetDto>();
        for(Tweet tweet:tweetList){
            newList.add(ModelMapperInstance.getModelMapper().map(tweet, TweetDto.class));
        }
        return(new BaseResponse(ResponseHeader.OK, new TweetListDto(newList)));
    }

}















