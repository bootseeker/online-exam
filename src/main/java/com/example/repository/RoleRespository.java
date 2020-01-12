package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Roles;

@Repository
public interface RoleRespository extends JpaRepository<Roles, Long>
{

	Roles findByRoleId(String roleId);
}
