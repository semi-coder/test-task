package com.spintech.testtask.service;

import com.spintech.testtask.entity.Movie;
import java.util.List;

public interface MovieService {

    Movie findMovieInLocalDb(String id);

    Movie findMovieInTmdb(String id);

    void saveMovieToLocalDb(Movie actor);

    List<Movie> getMoviesByActor(String actorId);

}
