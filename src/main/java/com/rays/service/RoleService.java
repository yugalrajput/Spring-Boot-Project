package com.rays.service;

import com.rays.dao.RoleDAO;
import com.rays.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Transactional(readOnly = true)
    public List search(RoleDTO dto,int pageNo,int pageSize) {
        List list=roleDAO.search(dto, pageNo, pageSize);
        return list;

    }
}
