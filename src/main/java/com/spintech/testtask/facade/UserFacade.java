package com.spintech.testtask.facade;

import com.spintech.testtask.entity.Movie;
import com.spintech.testtask.entity.User;
import java.util.List;

public interface UserFacade {

    boolean addFavouriteActor(String email, String password, String actorId);

    boolean addWatchedMovie(String email, String password, String movieId);

    boolean deleteFavouriteActor(String email, String password, String actorId);

    boolean deleteWatchedMovie(String email, String password, String actorId);

    List<Movie> getUnwatchedMoviesWithFavouriteActors(User user);

}
