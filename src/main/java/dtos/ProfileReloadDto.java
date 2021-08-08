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
public class ProfileReloadDto extends Dto{
    @JsonProperty("blockingsCount")
    private int blockingsCount;
    
    @JsonProperty("followersCount")
    private int followersCount;
    
    @JsonProperty("followingsCount")
    private int followingsCount;
    
    @JsonProperty("tweetsCount")
    private int tweetsCount;
    
    @JsonProperty("newFollowers")
    private List<PersonDto> newFollowers = new LinkedList<PersonDto>();
    
    @JsonProperty("newUnfollowers")
    private List<PersonDto> newUnfollowers = new LinkedList<PersonDto>();
    
    @JsonProperty("newRejectors")
    private List<PersonDto> newRejectors = new LinkedList<PersonDto>();

    @JsonProperty("yourRequests")
    private List<PersonDto> yourRequests = new LinkedList<PersonDto>();

    @JsonProperty("otherRequests")
    private List<ActionDto> otherRequests = new LinkedList<ActionDto>();
    
    public ProfileReloadDto(){
        
    }
}
