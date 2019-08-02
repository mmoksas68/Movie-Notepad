import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';


class AppHeader extends Component {
    constructor(props) {
        super(props);
        this.logoutHandler = this.logoutHandler.bind(this)
    }

    logoutHandler()
    {
        this.props.onLogout();
    }


    render(){
        const response = [];

        if(this.props.isAuthenticated){
            response.push(
                <Link className="nav-link" to={{ pathname: "/users/" + this.props.currentUser.id,
                    state: {
                        fromNotifications: true
                    }
                }}>{this.props.currentUser.username}</Link>
            );
            response.push(<i className="fas fa-sign-out-alt text-white ml-3mr-2" onClick={this.logoutHandler}>Logout</i>)
        }else{
            response.push(
                <Link to="/login"><i className="fas fa-sign-in-alt text-white ml-3 mr-2">-Login</i></Link>
            );
        }

        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <Link className="navbar-brand" to="/">MovieNotepad</Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <Link className="nav-link" to="/movies">Movies<span className="sr-only">(current)</span></Link>
                        </li>
                        <li className="nav-item active">
                            <Link className="nav-link" to="/users">Users<span className="sr-only">(current)</span></Link>
                        </li>
                        <li className="nav-item active">
                            <Link className="nav-link" to="/directors">Directors<span className="sr-only">(current)</span></Link>
                        </li>
                    </ul>
                    <form className="form-inline my-2 my-lg-0">
                        <input name="title" className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"/>
                            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search  </button>
                        {response}
                    </form>
                </div>
            </nav>
        )
    }


}
export default withRouter(AppHeader);