package controller.utility;

import controller.*;
import controller.utility.enums.ShowListChoices;
import dtos.CategoryDto;
import dtos.PersonDto;
import dtos.RoomDto;
import dtos.TweetDto;

import java.util.List;

public class ModelAccess {
    public static int currentPersonId;
    public static int tweetIdToTweetController;
    public static PersonDto personToPersonalPageController;
    public static RoomDto roomToChatBox;
    public static ShowListChoices showListChoice;
    public static List<PersonDto> peopleListToShow;
    public static TweetDto parentTweetToTweetMakingController;
    public static CategoryDto categoryToCategoryMessaging;
    public static MainMenuController mainMenuController;
    public static MessagingMainMenuController messagingMainMenuController;
    public static ExploreController exploreController;
    public static TimeLineController timeLineController;
    public static SettingController settingController;
    public static ProfileController profileController;
}
