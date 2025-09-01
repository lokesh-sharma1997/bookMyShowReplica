package com.bookmyshow.main.dto;
import java.util.List;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {
    @JsonProperty("_id")
    private Long id;
    private String name;
    @JsonIgnore
    private String location;
    @JsonProperty("cityId")
    private String city;
    private List<ScreenDto> screens; 

	
}

