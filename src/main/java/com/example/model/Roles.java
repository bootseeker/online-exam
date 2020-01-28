package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Roles 
{
	@Id
	@Column(name = "ROLE_ID")
	@JsonIgnore
	private long roleID;
	@Column(name = "ROLE_NAME")
	private String roleName;

}
