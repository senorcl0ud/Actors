package com.claude.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Actors")
public class Actor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "actor_id")
	private long actorId;
	
	@Column(name="name")
    private String name;
	
	@Column(name="description")	
	private String description;
	
	public Actor() {
		// TODO Auto-generated constructor stub
	}
	public Actor(long actorId, String name, String description) {
		this.actorId = actorId;
		this.name = name;
		this.description = description;
	}
	
	public long getActorId() {return actorId;}
	
	public void setActorId(long actorId) {this.actorId = actorId;}
	
	public String getName() {return name;}
	
	public void setName(String name) {this.name = name;}
	
	public String getDescription() {return description;}
	
	public void setDescription(String description) {this.description = description;}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}