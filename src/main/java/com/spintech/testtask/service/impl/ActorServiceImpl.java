package com.spintech.testtask.service.impl;

import com.google.gson.Gson;
import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.repository.ActorRepository;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private TmdbApi tmdbApi;

    private final Gson gson = new Gson();

    @Override
    @Transactional
    public Actor findActorInLocalDb(final String actorId) {
        return actorRepository.findById(actorId).orElse(null);
    }

    @Override
    public Actor findActorInTmdb(final String actorId) {
        final String responseBody = tmdbApi.getPersonById(actorId);
        if (isEmpty(responseBody)) {
            return null;
        }
        return gson.fromJson(responseBody, Actor.class);
    }

    @Override
    @Transactional
    public void saveActorToLocalDb(final Actor actor) {
        actorRepository.save(actor);
    }
}
