package controller.utility;

import controller.utility.enums.TimeLineParents;
import dtos.PersonDto;
import dtos.TweetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeLineParent {
    private TimeLineParents timeLineParents;
    private PersonDto person;
    private TweetDto tweet;
    public TimeLineParent(PersonDto person){
        this.person = person;
        this.tweet = null;
        this.timeLineParents = TimeLineParents.PERSON;
    }
    public TimeLineParent(TweetDto tweet){
        this.person = null;
        this.tweet = tweet;
        this.timeLineParents = TimeLineParents.TWEET;
    }
}
