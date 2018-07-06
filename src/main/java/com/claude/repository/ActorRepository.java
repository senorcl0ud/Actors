package com.claude.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.claude.entity.Actor;


public interface ActorRepository extends CrudRepository<Actor, Long> {
	Actor findByActorId(long actorId);
    List<Actor> findByNameAndDescription(String name, String description);
}
