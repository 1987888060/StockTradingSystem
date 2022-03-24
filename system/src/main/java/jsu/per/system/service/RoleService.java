package jsu.per.system.service;

import jsu.per.system.pojo.Role;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface RoleService {
    Role selectById(int id);

    List<Integer> findRight(int id);
}
