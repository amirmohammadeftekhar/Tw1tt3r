package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TweetListDto extends Dto  {
    @JsonProperty("tweetList")
    private List<TweetDto> tweetList = new LinkedList<TweetDto>();

    public  TweetListDto(){

    }

}
