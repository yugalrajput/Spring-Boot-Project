package com.rays.dao;

import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public RoleDAO roleDao;

    public void populate(UserDTO dto) {
        RoleDTO roleDTO = roleDao.findByPk(dto.getRoleId());
        dto.setRoleName(roleDTO.getName());

        if (dto.getId() != null && dto.getId() > 0) {
            UserDTO userData = findByPk(dto.getId());
            dto.setImageId(userData.getImageId());
        }
    }

    public long add(UserDTO dto) {
        populate(dto);
        entityManager.persist(dto);
        return dto.getId();
    }

    public void update(UserDTO dto) {
        populate(dto);
        entityManager.merge(dto);
    }

    public void delete(UserDTO dto) {
        entityManager.remove(dto);
    }

    public UserDTO findByPk(long pk) {
        UserDTO dto = entityManager.find(UserDTO.class, pk);
        return dto;
    }

    public UserDTO findByUniqueKey(String attribute, String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTO> criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);
        Root root = criteriaQuery.from(UserDTO.class);

        Predicate predicate = criteriaBuilder.equal(root.get(attribute), value);
        criteriaQuery.where(predicate);

        TypedQuery<UserDTO> query = entityManager.createQuery(criteriaQuery);
        List<UserDTO> list = query.getResultList();

        UserDTO dto = null;
        if (list.size() > 0) {
            dto = list.get(0);
        }

        return dto;
    }


    public List search(UserDTO dto, int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTO> criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);
        Root<UserDTO> root = criteriaQuery.from(UserDTO.class);
        List<Predicate> predicatelist = new ArrayList<Predicate>();

        if (dto != null) {

            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
                predicatelist.add(criteriaBuilder.like(root.get("firstName"), dto.getFirstName() + "%"));
            }

            if (dto.getId() != null && dto.getId() > 0) {
                predicatelist.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
            }

            if (dto.getDob() != null && dto.getDob().getTime() > 0) {
                predicatelist.add(criteriaBuilder.equal(root.get("dob"), dto.getDob()));
            }
        }

        criteriaQuery.where(predicatelist.toArray(new Predicate[predicatelist.size()]));
        TypedQuery<UserDTO> query = entityManager.createQuery(criteriaQuery);

        if (pageSize > 0) {
            query.setFirstResult(pageNo * pageSize);
            query.setMaxResults(pageSize);
        }
        List<UserDTO> list = query.getResultList();
        return list;


    }

}
