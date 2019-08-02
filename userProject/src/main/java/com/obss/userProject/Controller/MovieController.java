package com.obss.userProject.Controller;

import com.obss.userProject.classes.Movie;
import com.obss.userProject.classes.requestClasses.MovieRequest;
import com.obss.userProject.security.CurrentUser;
import com.obss.userProject.security.UserPrincipal;
import com.obss.userProject.service.DirectorService;
import com.obss.userProject.service.MovieImportService;
import com.obss.userProject.service.MovieService;
import com.obss.userProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @Autowired
    DirectorService directorService;

    @Autowired
    MovieImportService movieImportService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/addWatched")
    public boolean addWatchedMovie(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        return userService.addWatchedMovie(movieService.getMovieById(id).getTitle(), currentUser.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/addFavorite")
    public boolean addFavoriteMovie(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        return userService.addFavoriteMovie(movieService.getMovieById(id).getTitle(), currentUser.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/removeWatched")
    public boolean removeWatchedMovie(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        return userService.removeWatchedMovie(movieService.getMovieById(id).getTitle(), currentUser.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/removeFavorite")
    public boolean removeFavoriteMovie(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        return userService.removeFavoriteMovie(movieService.getMovieById(id).getTitle(), currentUser.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Iterable<Movie> listMovies(){
        return movieService.getMovies();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public Iterable<Movie> searchMovies(@RequestParam String title)
    {
        return movieService.getMoviesByTitle(title);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Movie getMovie(@PathVariable("id") Long id){
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie){
        return movieService.updateMovie(movie, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(@PathVariable("id") Long id)
    {
        movieService.delete(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMovie(@RequestBody MovieRequest movieRequest){
        Movie movie = movieImportService.importMovie(movieRequest.getTitle());
        if(movie != null){
            movieService.addMovie(movie);
            directorService.addDirectorByMovie(movie.getTitle());
            return new ResponseEntity<>("Movie is added to list", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Couldn't add the movie", HttpStatus.BAD_REQUEST);

    }
}
