package com.databases.shop.mapstruct.dtos.dataDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
public class CustomerFilterBoundsDto {

    @JsonProperty("max_overall")
    @Min(0)
    private int maxOverall;

    @JsonProperty("max_avg")
    @Min(0)
    private double maxAvg;

}
