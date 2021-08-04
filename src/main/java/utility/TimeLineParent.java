package utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import utility.enums.TimeLineParents;

@Getter
@Setter
public class TimeLineParent {
    @JsonProperty("timeLineParents")
    private TimeLineParents timeLineParents;
    @JsonProperty("personId")
    private int personId;
    @JsonProperty("tweetId")
    private int tweetId;

    public TimeLineParent(int id, TimeLineParents timeLineParents){
        if(timeLineParents == TimeLineParents.PERSON){
            personId = id;
        }
        else{
            tweetId = id;
        }
        this.timeLineParents = timeLineParents;
    }

    public TimeLineParent(){

    }
}
