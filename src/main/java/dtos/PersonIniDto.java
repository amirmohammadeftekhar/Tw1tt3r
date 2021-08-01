package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonIniDto extends Dto{
    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("toShowEmail")
    private boolean toShowEmail;

    public PersonIniDto(){

    }
}
