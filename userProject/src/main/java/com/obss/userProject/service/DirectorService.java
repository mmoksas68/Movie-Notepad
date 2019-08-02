package com.obss.userProject.service;

import com.obss.userProject.classes.Director;
import com.obss.userProject.classes.Movie;
import com.obss.userProject.dao.DirectorRepository;
import com.obss.userProject.payload.SimpleDirector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DirectorService {
    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieService movieService;

    public Director addDirectorByMovie(String title){
        Movie movie = movieService.getMovieByTitle(title);
        SimpleDirector simpleDirector = fullNameToSimple(movie.getDirector());
        Director directorAlreadySaved = directorRepository.findDistinctFirstByNameAndSurname(simpleDirector.getName(), simpleDirector.getSurname());
        if( directorAlreadySaved == null) {
            Director director = new Director();
            director.setName(simpleDirector.getName());
            director.setSurname(simpleDirector.getSurname());
            director.setBirthDate(new Date(5000));
            director.setBirthPlace("Texas");
            Set<Movie> movies = new HashSet<>();
            movies.add(movie);
            director.setMovies(movies);
            directorRepository.save(director);
            return director;
         }else{
            Set<Movie> movies = directorAlreadySaved.getMovies();
            movies.add(movie);
            directorAlreadySaved.setMovies(movies);
            directorRepository.save(directorAlreadySaved);
            return directorAlreadySaved;
        }
    }

    public SimpleDirector fullNameToSimple(String fullName){
        String[] directorName = fullName.split(" ");
        String name = "";
        String lastName = "";
        for(int i = 0; i < directorName.length; i++)
        {
            if(i == 0)
                name = directorName[i];
            if(i == directorName.length - 1)
                lastName = directorName[i];
        }
        SimpleDirector simpleDirector = new SimpleDirector();
        simpleDirector.setName(name);
        simpleDirector.setSurname(lastName);
        return simpleDirector;
    }

    public Iterable<Director> getDirectors(){
        return directorRepository.findAll();
    }

    public Director addDirector(Director director){
        if( directorRepository.findDistinctFirstByNameAndSurname(director.getName(), director.getSurname()) == null ||
            !directorRepository.findDistinctFirstByNameAndSurname(director.getName(),
                                                                  director.getSurname()).getBirthDate().equals(director.getBirthDate())){
            return directorRepository.save(director);
        }
        else
          return null;
    }

    public Director updateMoviesList(Director director){
        return directorRepository.save(director);
    }


    public Director getDirectorById(Long id){
        return directorRepository.findById(id).get();
    }

    public List<Director> getDirectorsByName(String name){
        return directorRepository.findAllByNameContaining(name);
    }

    public boolean updateDirector(Director director, Long id){
        Director tempDirector = directorRepository.findById(id).get();
        if(tempDirector != null){
            tempDirector.setBirthPlace(director.getBirthPlace());
            tempDirector.setBirthDate(director.getBirthDate());
            tempDirector.setName(director.getName());
            tempDirector.setSurname(director.getSurname());
            directorRepository.save(tempDirector);
            return true;
        }else
            return false;
    }

    public void delete(Long id) {
        directorRepository.deleteById(id);
    }
}
