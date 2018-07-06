package com.claude.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.claude.actors_ws.ActorInfo;
import com.claude.actors_ws.AddActorRequest;
import com.claude.actors_ws.AddActorResponse;
import com.claude.actors_ws.DeleteActorRequest;
import com.claude.actors_ws.DeleteActorResponse;
import com.claude.actors_ws.GetActorByIdRequest;
import com.claude.actors_ws.GetActorByIdResponse;
import com.claude.actors_ws.GetAllActorsResponse;
import com.claude.actors_ws.ServiceStatus;
import com.claude.actors_ws.UpdateActorRequest;
import com.claude.actors_ws.UpdateActorResponse;
import com.claude.entity.Actor;
import com.claude.service.IActorService;


@Endpoint
public class ActorEndpoint {
	private static final String NAMESPACE_URI = "http://www.concretepage.com/actor-ws";
	@Autowired
	private IActorService actorService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getActorByIdRequest")
	@ResponsePayload
	public GetActorByIdResponse getActor(@RequestPayload GetActorByIdRequest request) {
		GetActorByIdResponse response = new GetActorByIdResponse();
		ActorInfo actorInfo = new ActorInfo();
		BeanUtils.copyProperties(actorService.getActorById(request.getActorId()), actorInfo);
		response.setActorInfo(actorInfo);
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllactorsRequest")
	@ResponsePayload
	public GetAllActorsResponse getAllactors() {
		GetAllActorsResponse response = new GetAllActorsResponse();
		List<ActorInfo> actorInfoList = new ArrayList<>();
		List<Actor> actorList = actorService.getAllActors();
		for (int i = 0; i < actorList.size(); i++) {
		     ActorInfo ob = new ActorInfo();
		     BeanUtils.copyProperties(actorList.get(i), ob);
		     actorInfoList.add(ob);    
		}
		response.getActorInfo().addAll(actorInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addactorRequest")
	@ResponsePayload
	public AddActorResponse addactor(@RequestPayload AddActorRequest request) {
		AddActorResponse response = new AddActorResponse();		
    	        ServiceStatus serviceStatus = new ServiceStatus();		
		Actor actor = new Actor();
		actor.setName(request.getName());
		actor.setDescription(request.getDescription());		
                boolean flag = actorService.addActor(actor);
                if (flag == false) {
        	   serviceStatus.setStatusCode("CONFLICT");
        	   serviceStatus.setMessage("Content Already Available");
        	   response.setServiceStatus(serviceStatus);
                } else {
		   ActorInfo actorInfo = new ActorInfo();
	           BeanUtils.copyProperties(actor, actorInfo);
		   response.setActorInfo(actorInfo);
        	   serviceStatus.setStatusCode("SUCCESS");
        	   serviceStatus.setMessage("Content Added Successfully");
        	   response.setServiceStatus(serviceStatus);
                }
                return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateactorRequest")
	@ResponsePayload
	public UpdateActorResponse updateactor(@RequestPayload UpdateActorRequest request) {
		Actor actor = new Actor();
		BeanUtils.copyProperties(request.getActorInfo(), actor);
		actorService.updateActor(actor);
    	        ServiceStatus serviceStatus = new ServiceStatus();
    	        serviceStatus.setStatusCode("SUCCESS");
    	        serviceStatus.setMessage("Content Updated Successfully");
    	        UpdateActorResponse response = new UpdateActorResponse();
    	        response.setServiceStatus(serviceStatus);
    	        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteActorRequest")
	@ResponsePayload
	public DeleteActorResponse deleteActor(@RequestPayload DeleteActorRequest request) {
		Actor actor = actorService.getActorById(request.getActorId());
    	        ServiceStatus serviceStatus = new ServiceStatus();
		if (actor == null ) {
	    	    serviceStatus.setStatusCode("FAIL");
	    	    serviceStatus.setMessage("Content Not Available");
		} else {
		    actorService.deleteActor(actor);
	    	    serviceStatus.setStatusCode("SUCCESS");
	    	    serviceStatus.setMessage("Content Deleted Successfully");
		}
    	        DeleteActorResponse response = new DeleteActorResponse();
    	        response.setServiceStatus(serviceStatus);
		return response;
	}	
} 
