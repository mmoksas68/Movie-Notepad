package com.obss.userProject.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(unique=true, nullable = false)
    private String title;
    @Column
    private String runtime;
    @Column
    private Float imdbRating;
    @Column
    private String released;
    @Column
    private String genre;
    @Column
    private String director;
    @Column
    private String poster;

    @ManyToMany(mappedBy = "watchedMovies")
    @JsonIgnore
    private Set<MyUser> usersWatched = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteMovies")
    @JsonIgnore
    private Set<MyUser> usersFavorite = new HashSet<>();

    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private Set<Director> directorDB = new HashSet<>();

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", runtime='" + runtime + '\'' +
                ", imdbRating=" + imdbRating +
                ", released='" + released + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                '}';
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Set<MyUser> getUsersWatched() {
        return usersWatched;
    }

    public void setUsersWatched(Set<MyUser> usersWatched) {
        this.usersWatched = usersWatched;
    }

    public Set<MyUser> getUsersFavorite() {
        return usersFavorite;
    }

    public void setUsersFavorite(Set<MyUser> usersFavorite) {
        this.usersFavorite = usersFavorite;
    }

    public Set<Director> getDirectorDB() {
        return directorDB;
    }

    public void setDirectorDB(Set<Director> directorDB) {
        this.directorDB = directorDB;
    }

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

}
