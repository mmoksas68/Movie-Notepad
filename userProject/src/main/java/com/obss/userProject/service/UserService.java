package com.obss.userProject.service;

import com.obss.userProject.classes.Movie;
import com.obss.userProject.classes.MyUser;
import com.obss.userProject.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    public Iterable<MyUser> users()
    {
        return userRepository.findAll();
    }

    public boolean addWatchedMovie(String title, Long id){
        MyUser user = userRepository.findById(id).get();
        Movie movie = movieService.getMovieByTitle(title);
        if(user != null && movie != null){
            Set<Movie> watchedMovies = user.getWatchedMovies();
            watchedMovies.add(movie);
            user.setWatchedMovies(watchedMovies);
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }

    public boolean removeWatchedMovie(String title, Long id) {
        MyUser user = userRepository.findById(id).get();
        Movie movie = movieService.getMovieByTitle(title);
        user.setWatchedMovies(movieService.safeDelete(user.getWatchedMovies(), movie.getId()));
        if(userRepository.save(user) != null)
            return true;
        else
            return false;
    }

    public boolean removeFavoriteMovie(String title, Long id) {
        MyUser user = userRepository.findById(id).get();
        Movie movie = movieService.getMovieByTitle(title);
        user.setFavoriteMovies(movieService.safeDelete(user.getFavoriteMovies(), movie.getId()));
        if(userRepository.save(user) != null)
            return true;
        else
            return false;
    }

    public boolean addFavoriteMovie(String title, Long id){
        MyUser user = userRepository.findById(id).get();
        Movie movie = movieService.getMovieByTitle(title);
        if(user != null && movie != null){
            Set<Movie> favoriteMovies = user.getFavoriteMovies();
            favoriteMovies.add(movie);
            user.setFavoriteMovies(favoriteMovies);
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }

    public MyUser findUserByUsername(String username){
        return userRepository.findDistinctFirstByUsername(username);
    }

    public MyUser findUserById(Long id)
    {
        return userRepository.findById(id).get();
    }

    public MyUser updateMoviesList(MyUser user){
        return userRepository.save(user);
    }

    public boolean updateUser(MyUser user, Long id){
        if( userRepository.findById(id) != null)
        {
            MyUser tempUser = userRepository.findById(id).get();
            if(tempUser != null){
                tempUser.setFirstName(user.getFirstName());
                tempUser.setLastName(user.getLastName());
                tempUser.setUsername(user.getUsername());
                tempUser.setEmail(user.getEmail());
                userRepository.save(tempUser);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


}
