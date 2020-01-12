package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Roles 
{
	@Id
	@Column(name = "ROLE_SEQ")
	@JsonIgnore
	private long roleSeq;
	@Column(name = "ROLE_ID")
	private String roleId;

}
