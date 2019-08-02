package com.obss.userProject.service;

import com.obss.userProject.classes.Director;
import com.obss.userProject.classes.Movie;
import com.obss.userProject.classes.MyUser;
import com.obss.userProject.dao.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserService userService;

    @Autowired
    DirectorService directorService;

    public Iterable<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie){
        if( movieRepository.findDistinctFirstByTitle(movie.getTitle()) == null){
            return movieRepository.save(movie);
        }
        else
            return null;
    }



    public Movie getMovieById(Long id){
        return movieRepository.findById(id).get();
    }

    public Movie getMovieByTitle(String title){ return movieRepository.findDistinctFirstByTitle(title);}

    public List<Movie> getMoviesByTitle(String title){
        return movieRepository.findAllByTitleContaining(title);
    }

    public boolean updateMovie(Movie movie, Long id){
        Movie tempMovie = movieRepository.findById(id).get();
        if(tempMovie != null){
            tempMovie.setRuntime(movie.getRuntime());
            tempMovie.setImdbRating(movie.getImdbRating());
            tempMovie.setReleased(movie.getReleased());
            tempMovie.setTitle(movie.getTitle());
            tempMovie.setGenre(movie.getGenre());
            movieRepository.save(tempMovie);
            return true;
        }
        else
            return false;

    }

    public Set<Movie> safeDelete(Set<Movie> movieList, Long id){
        Movie movie = movieRepository.findById(id).get();
        movieList.remove(movie);
        return movieList;
    }

    public <T> List<T> iterate(Set<T> hashSet){
        List<T> deleteList = new ArrayList<>();
        for (Iterator<T> it = hashSet.iterator(); it.hasNext(); ) {
            deleteList.add(it.next());
        }
        return deleteList;
    }

    public void delete(Long id){
        Movie movie = movieRepository.findById(id).get();
        Set<Director> directors = movie.getDirectorDB();
        Set<MyUser> usersWatched = movie.getUsersWatched();
        Set<MyUser> usersFavorite = movie.getUsersFavorite();

        for(MyUser user: iterate(usersFavorite)){
            user.setFavoriteMovies(safeDelete(user.getFavoriteMovies(), id));
            userService.updateMoviesList(user);
        }

        for(MyUser user: iterate(usersWatched)){
            user.setWatchedMovies(safeDelete(user.getWatchedMovies(), id));
            userService.updateMoviesList(user);
        }

        for(Director director: iterate(directors)){
            director.setMovies(safeDelete(director.getMovies(), id));
            directorService.updateMoviesList(director);
        }

        movieRepository.deleteById(id);
    }
}
