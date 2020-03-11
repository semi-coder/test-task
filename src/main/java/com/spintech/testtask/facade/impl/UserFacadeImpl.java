package com.spintech.testtask.facade.impl;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.Movie;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.facade.UserFacade;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.MovieService;
import com.spintech.testtask.service.UserService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private MovieService movieService;

    @Override
    public boolean addFavouriteActor(final String email,
                                     final String password,
                                     final String actorId) {
        final User user = userService.findUser(email, password);
        final Actor actor = getActor(actorId);
        if (isNull(user) || isNull(actor)) {
            return false;
        }
        user.getFavouriteActors().add(actor);
        userService.saveUser(user);
        return true;
    }

    @Override
    public boolean addWatchedMovie(final String email,
                                   final String password,
                                   final String movieId) {
        final User user = userService.findUser(email, password);
        final Movie movie = getMovie(movieId);
        if (isNull(user) || isNull(movie)) {
            return false;
        }
        user.getWatchedMovies().add(movie);
        System.out.println("[Add watched movie] " + user.getWatchedMovies());
        userService.saveUser(user);
        return true;
    }

    @Override
    public boolean deleteFavouriteActor(String email, String password, String actorId) {
        final User user = userService.findUser(email, password);
        if(isNull(user)) {
            return false;
        }
        final Set<Actor> favouriteActors = user.getFavouriteActors();
        boolean isActorRemoved = favouriteActors.removeIf(actor -> actorId.equals(actor.getId()));
        if(isActorRemoved) {
            userService.saveUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteWatchedMovie(final String email,
                                      final String password,
                                      final String movieId) {
        final User user = userService.findUser(email, password);
        if(isNull(user)) {
            return false;
        }
        final Set<Movie> watchedMovies = user.getWatchedMovies();
        boolean isMovieRemoved = watchedMovies.removeIf(movie -> movieId.equals(movie.getId()));
        if(isMovieRemoved) {
            userService.saveUser(user);
            System.out.println("[Delete watched movie] " + watchedMovies);
            return true;
        }
        return false;
    }

    @Override
    public List<Movie> getUnwatchedMoviesWithFavouriteActors(final User user) {
        final Set<Actor> favouriteActors = user.getFavouriteActors();
        final Set<Movie> watchedMovies = user.getWatchedMovies();

        return favouriteActors.stream()
                .map(actor -> movieService.getMoviesByActor(actor.getId()))
                .flatMap(Collection::stream)
                .filter(movie -> !watchedMovies.contains(movie))
                .collect(Collectors.toList());
    }

    private Actor getActor(final String actorId) {
        Actor actor = actorService.findActorInLocalDb(actorId);
        if(!isNull(actor)) {
            return actor;
        }
        actor = actorService.findActorInTmdb(actorId);
        if(!isNull(actor)) {
            actorService.saveActorToLocalDb(actor);
        }
        return actor;
    }

    private Movie getMovie(final String movieId) {
        Movie movie = movieService.findMovieInLocalDb(movieId);
        if(!isNull(movie)) {
            return movie;
        }
        movie = movieService.findMovieInTmdb(movieId);
        if(!isNull(movie)) {
            movieService.saveMovieToLocalDb(movie);
        }
        return movie;
    }

}
