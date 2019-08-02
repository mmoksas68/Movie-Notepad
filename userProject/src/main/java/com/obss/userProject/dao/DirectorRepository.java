package com.obss.userProject.dao;

import com.obss.userProject.classes.Director;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {
    Director findDistinctFirstByNameAndSurname(String name, String surname);
    List<Director> findAllByNameContaining(String name);
}
