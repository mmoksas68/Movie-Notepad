import React from 'react';
import {getUsers, deleteUser} from '../util/APIUtils';
import {Redirect} from 'react-router-dom';

class UsersPage extends React.Component{
    constructor(props) {
        super(props);
        this.state = {users: [], message: ''}
        this.clickHandler = this.clickHandler.bind(this)
    }

    componentDidMount() {
        getUsers().then(result => {
            this.setState({users: result});
        }).catch(error =>{
            this.setState( {message: 'Sorry! Something went wrong. Please try again!'})
        });
    }

    clickHandler(id){
        deleteUser(id).then(result => {
           console.log(result)
        }).catch(error =>{
           console.log('Sorry! Something went wrong. Please try again!')
        });
    }

    render() {
        const style = {
            width: "18rem"
        };

        this.state.users.map(user => console.log(user));
        if(this.props.isAuthenticated){
            return(
                <div className="row">
                    {this.state.users.map(user =>
                        <div>
                            <div className="card" style={{width: "18rem"}}>
                                <img src="https://previews.123rf.com/images/panyamail/panyamail1809/panyamail180900343/109879063-user-avatar-icon-sign-profile-symbol.jpg" className="card-img-top" alt="..."/>
                                <div className="card-body">
                                    <h5 className="card-title">@{user.username}</h5>
                                    <p className="card-text">(Burası sonra kısa biyografi olacak)Some quick example text to build on the card title and make up
                                        the bulk of the card's content.</p>
                                </div>
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item">{user.firstName}</li>
                                    <li className="list-group-item">{user.lastName}</li>
                                    <li className="list-group-item">{user.email}</li>
                                </ul>
                                <div className="card-body">
                                    <button type="button" className="btn btn-primary">Update</button>
                                    <button type="button" className="ml-3 btn btn-primary" onClick={ () => this.clickHandler(user.id)}>Delete</button>
                                </div>
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

export default UsersPage;