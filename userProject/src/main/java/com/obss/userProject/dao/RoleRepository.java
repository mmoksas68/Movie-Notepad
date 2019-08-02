package com.obss.userProject.dao;

import com.obss.userProject.classes.Role;
import com.obss.userProject.classes.requestClasses.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(RoleName roleUser);
}
