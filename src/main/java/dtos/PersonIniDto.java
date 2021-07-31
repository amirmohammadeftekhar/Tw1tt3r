package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public PersonIniDto(String firstname, String lastName, String userName, String password, String emailAddress,
                        boolean isPrivate, boolean toShowEmail) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.isPrivate = isPrivate;
        this.toShowEmail = toShowEmail;
    }

    public PersonIniDto(){

    }
}
