package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@Slf4j
public class TmdbApiImpl implements TmdbApi {

    private static final String TV_POPULAR_URL = "/tv/popular";
    private static final String PERSON_URL = "/person/";
    private static final String MOVIE_URL = "/movie/";
    private static final String ACTOR_CAST_URL_FORMAT = "/person/%s/movie_credits";
    private static final String COULDNT_GET_REQUESTED_DATA = "Couldn't get requested data";

    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    @Override
    public String popularTVShows() throws IllegalArgumentException {
        return performSafeGetRequest(TV_POPULAR_URL);
    }

    @Override
    public String getPersonById(final String id) {
        return performSafeGetRequest(PERSON_URL, id);
    }

    @Override
    public String getMovieById(final String id) {
        return performSafeGetRequest(MOVIE_URL, id);
    }

    @Override
    public String getCastForActor(final String actorId) {
        return performSafeGetRequest(String.format(ACTOR_CAST_URL_FORMAT, actorId));
    }

    private String performSafeGetRequest(final String url) {
        try {
            return performGetRequest(getTmdbUrl(url));
        } catch (URISyntaxException e) {
            log.error(COULDNT_GET_REQUESTED_DATA, e);
        }
        return null;
    }

    private String performSafeGetRequest(final String url, final String pathParam) {
        try {
            return performGetRequest(getTmdbUrl(url, pathParam));
        } catch (URISyntaxException e) {
            log.error(COULDNT_GET_REQUESTED_DATA, e);
        }
        return null;
    }

    private String performGetRequest(final String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        final StringBuilder builder = new StringBuilder(tmdbApiBaseUrl)
                .append(tmdbItem);
        return addMandatoryParametersRoUrl(builder.toString());
    }

    private String getTmdbUrl(final String tmdbItem, final String pathParam) throws URISyntaxException {
        final StringBuilder builder = new StringBuilder(tmdbApiBaseUrl)
                .append(tmdbItem)
                .append(pathParam);
        return addMandatoryParametersRoUrl(builder.toString());
    }

    private String addMandatoryParametersRoUrl(final String url) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        return uriBuilder.build().toString();
    }
}
