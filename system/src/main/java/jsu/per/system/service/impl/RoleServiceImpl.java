package jsu.per.system.service.impl;

import jsu.per.system.dao.RoleMapper;
import jsu.per.system.pojo.Role;
import jsu.per.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role selectById(int id) {
        return roleMapper.selectById(id);
    }
}
