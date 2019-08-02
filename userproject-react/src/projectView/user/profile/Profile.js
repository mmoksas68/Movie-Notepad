import React, { Component } from 'react';
import { getUserProfile } from '../../util/APIUtils';
import { formatDate } from '../../util/Helpers';
import LoadingIndicator  from '../../common/LoadingIndicator';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';


class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            isLoading: false
        }
        this.loadUserProfile = this.loadUserProfile.bind(this);
    }

    loadUserProfile(username) {
        this.setState({
            isLoading: true
        });

        getUserProfile(username)
            .then(response => {
                this.setState({
                    user: response,
                    isLoading: false
                });
            }).catch(error => {
            if(error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });
            }
        });
    }

    componentDidMount() {
        const username = this.props.match.params.username;
        this.loadUserProfile(username);
    }

    componentDidUpdate(nextProps) {
        if(this.props.match.params.username !== nextProps.match.params.username) {
            this.loadUserProfile(nextProps.match.params.username);
        }
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        console.log(this.state.user);
        const style = {
            width: "18rem"
        };
        if(this.state.user)
        return (
            <div>
                <div className="card" style={{width: "18rem"}}>
                    <img src="https://previews.123rf.com/images/panyamail/panyamail1809/panyamail180900343/109879063-user-avatar-icon-sign-profile-symbol.jpg" className="card-img-top" alt="..."/>
                    <div className="card-body">
                        <h5 className="card-title">@{this.state.user.username}</h5>
                        <p className="card-text">(Burası sonra kısa biyografi olacak)Some quick example text to build on the card title and make up
                            the bulk of the card's content.</p>
                    </div>
                    <ul className="list-group list-group-flush">
                        <li className="list-group-item">{this.state.user.firstName}</li>
                        <li className="list-group-item">{this.state.user.lastName}</li>
                        <li className="list-group-item">{this.state.user.email}</li>
                    </ul>
                    <div className="card-body">
                        <a href="#" className="card-link">Update</a>
                        <a href="#" className="card-link">Delete</a>
                    </div>
                </div>
            </div>
        );
        else
            return null
    }
}

export default Profile;