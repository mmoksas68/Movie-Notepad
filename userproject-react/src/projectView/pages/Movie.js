import React from 'react';
import {getMovies} from '../util/APIUtils';
import {Link} from 'react-router-dom'
import {Redirect} from 'react-router-dom';

class Movie extends React.Component{
    constructor(props) {
        super(props);
        this.state = {movies: [], message: ''}
    }

    componentDidMount() {
        getMovies().then(result => {
            this.setState({movies: result});
        }).catch(error =>{
           // this.props.history.push("/login");
            this.setState( {message: 'Sorry! Something went wrong. Please try again!'})
        });
    }

    render() {
        const style = {
            width: "18rem"
        };

        this.state.movies.map(movie => console.log(movie));
        if(this.props.isAuthenticated){
            return(
                <div className="row">
                    {this.state.movies.map(movie =>
                        <div className="card col-sm-4" style={style}>
                            <img src={movie.poster} className="card-img-top" alt="..."/>
                            <div className="card-body">
                                <h5 className="card-title">{movie.title}</h5>
                                <p className="card-text">Director: {movie.director}</p>
                                <p className="card-text">Genre: {movie.genre}</p>
                                <p className="card-text">Rating: {movie.imdbRating} </p>
                                <p className="card-text">Released: {movie.released} </p>
                                <p className="card-text">Duration: {movie.runtime} </p>
                                <Link to="/movies/:movie.id" className="btn btn-primary">Go somewhere</Link>
                            </div>
                        </div>
                    )}
                </div>
            )
        }
        else
            return <Redirect to='/login'  />;

    }
}

export default Movie;