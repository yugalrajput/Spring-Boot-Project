package com.rays.dao;

import com.rays.dto.RoleDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDAO {

    @PersistenceContext
    public EntityManager entityManager;


    public RoleDTO findByPk(long pk) {
        RoleDTO dto = entityManager.find(RoleDTO.class, pk);
        return dto;

    }
}
