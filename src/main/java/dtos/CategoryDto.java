package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        resolver = Resolver.class,
        scope = CategoryDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto extends Dto {
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CategoryDto))
            return false;

        CategoryDto other = (CategoryDto) o;

        return (Id == other.getId());
    }


    public CategoryDto(){

    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
