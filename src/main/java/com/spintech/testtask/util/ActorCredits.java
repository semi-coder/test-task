package com.spintech.testtask.util;

import com.spintech.testtask.entity.Movie;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ActorCredits {

    private List<Movie> cast;

}
