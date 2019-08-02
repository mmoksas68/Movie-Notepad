import React from 'react';
import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import AppHeader from './common/AppHeader';
import NotFound from './common/NotFound';
import LoadingIndicator from './common/LoadingIndicator';
import Login from './user/login/Login'
import PrivateRoute from './common/PrivateRoute';
import Signup from './user/signup/Signup';
import Profile from './user/profile/Profile';
import MainPage from './pages/MainPage'
import Movie from './pages/Movie'
import UsersPage from './pages/UsersPage'

import {
    Route,
    withRouter,
    Switch
} from 'react-router-dom';


class AppRouter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: false
        }
        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
    }

    loadCurrentUser() {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentDidMount() {
        this.loadCurrentUser();
    }

    handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });
        console.log("logged out")
        this.props.history.push(redirectTo);

    }

    handleLogin() {
        this.loadCurrentUser();
        this.props.history.push("/movies");
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }
        return (
            <div className="app-container">
               <AppHeader isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           onLogout={this.handleLogout}/>

                <div className="app-content">
                    <div className="container">
                        <Switch>
                            <Route exact path="/"
                                   render={(props) => <MainPage isAuthenticated={this.state.isAuthenticated}
                                                                currentUser={this.state.currentUser} {...props} />}>
                            </Route>
                            <Route exact path="/movies"
                                   render={(props) => <Movie isAuthenticated={this.state.isAuthenticated}
                                                              currentUser={this.state.currentUser}/>}/>
                            <Route path="/login"
                                   render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>

                            <Route path="/signup"
                                   render={(props) => <Signup isAuthenticated={this.state.isAuthenticated}
                                                                    currentUser={this.state.currentUser}/>}/>
                            <Route path="/users"
                                   render={(props) => <UsersPage isAuthenticated={this.state.isAuthenticated}
                                                              currentUser={this.state.currentUser}/>}/>
                            <Route path="/users/:username"
                                   render={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/poll/new" component={Movie} handleLogout={this.handleLogout}></PrivateRoute>

                            <Route component={NotFound}/>
                        </Switch>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(AppRouter);