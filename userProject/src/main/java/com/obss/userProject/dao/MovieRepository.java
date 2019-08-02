package com.obss.userProject.dao;

import com.obss.userProject.classes.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findDistinctFirstByTitle(String title);
    List<Movie> findAllByTitleContaining(String title);
}
