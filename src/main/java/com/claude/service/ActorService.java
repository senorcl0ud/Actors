package com.claude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claude.entity.Actor;
import com.claude.repository.ActorRepository;


@Service
public class ActorService implements IActorService {
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Override
	public Actor getActorById(long actorId) {
		Actor Actor = actorRepository.findByActorId(actorId);
		return Actor;
	}	
	@Override
	public List<Actor> getAllActors(){
		List<Actor> list = new ArrayList<>();
		actorRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addActor(Actor actor){
	        List<Actor> list = actorRepository.findByNameAndDescription(actor.getName(), actor.getDescription()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	           actor = actorRepository.save(actor);
    	           return true;
                }
	}
	@Override
	public void updateActor(Actor actor) {
		actorRepository.save(actor);
	}
	
	@Override
	public void deleteActor(Actor actor) {
		actorRepository.delete(actor);
	}

}
