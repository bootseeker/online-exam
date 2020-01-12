package com.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public  class  User implements Serializable {

	private static final long serialVersionUID = 4158685983281414426L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@SequenceGenerator(sequenceName = "USER_SEQ", allocationSize = 1, name = "USER_SEQ")
	@Column(name = "user_id")
	private long userId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "phone_no")
	private String phoneNo;
	@Column(name = "address")
	private String address;
	@Column(name = "password")
	private String password;
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "IS_ACTIVE", nullable = true)
	private boolean isActive;
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ROLE_SEQ")
	private Roles role;
	

	
}
