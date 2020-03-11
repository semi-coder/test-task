package com.spintech.testtask.service;

import com.spintech.testtask.entity.Actor;

public interface ActorService {

    Actor findActorInLocalDb(String id);

    Actor findActorInTmdb(String id);

    void saveActorToLocalDb(Actor actor);

}
