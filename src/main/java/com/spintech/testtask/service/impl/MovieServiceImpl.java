package com.spintech.testtask.service.impl;

import com.google.gson.Gson;
import com.spintech.testtask.entity.Movie;
import com.spintech.testtask.repository.MovieRepository;
import com.spintech.testtask.service.MovieService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import com.spintech.testtask.util.ActorCredits;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TmdbApi tmdbApi;

    private final Gson gson = new Gson();

    @Override
    @Transactional
    public Movie findMovieInLocalDb(final String actorId) {
        return movieRepository.findById(actorId).orElse(null);
    }

    @Override
    public Movie findMovieInTmdb(final String movieId) {
        final String responseBody = tmdbApi.getMovieById(movieId);
        if (isEmpty(responseBody)) {
            return null;
        }
        return gson.fromJson(responseBody, Movie.class);
    }

    @Override
    @Transactional
    public void saveMovieToLocalDb(final Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMoviesByActor(final String actorId) {
        final String response = tmdbApi.getCastForActor(actorId);
        if (isEmpty(response)) {
            return Collections.emptyList();
        }
        final ActorCredits credits = gson.fromJson(response, ActorCredits.class);
        return credits.getCast();
    }
}
