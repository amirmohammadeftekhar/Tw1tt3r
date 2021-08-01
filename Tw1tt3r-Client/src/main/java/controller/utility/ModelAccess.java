package controller.utility;

import controller.utility.enums.ShowListChoices;
import dtos.CategoryDto;
import dtos.PersonDto;
import dtos.RoomDto;
import dtos.TweetDto;

import java.util.List;

public class ModelAccess {
    public static int currentPersonId;
    public static TweetDto tweetToTweetController;
    public static PersonDto personToPersonalPageController;
    public static RoomDto roomToChatBox;
    public static ShowListChoices showListChoice;
    public static List<PersonDto> peopleListToShow;
    public static TweetDto parentTweetToTweetMakingController;
    public static CategoryDto categoryToCategoryMessaging;
}
