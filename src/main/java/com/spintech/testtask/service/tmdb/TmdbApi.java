package com.spintech.testtask.service.tmdb;

public interface TmdbApi {
    String popularTVShows();

    String getPersonById(String id);

    String getMovieById(String id);

    String getCastForActor(String actorId);
}
