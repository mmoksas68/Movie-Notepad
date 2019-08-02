import React from 'react';
import {Redirect} from 'react-router-dom';
import {login} from "../../util/APIUtils";
import {ACCESS_TOKEN} from "../../../constants";

class Login extends React.Component{
    constructor(props){
        super(props)
        this.state = {usernameOrEmail: '', password: '', failed: ''}
        this.handleChange = this.handleChange.bind(this)
        this.clickHandler = this.clickHandler.bind(this)
    }

    handleChange(event){
        this.setState({[event.target.name]: event.target.value});
    }

    clickHandler(){
        const loginRequest = {
            usernameOrEmail: this.state.usernameOrEmail,
            password: this.state.password
        };
        login(loginRequest)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                this.props.onLogin();
            }).catch(error => {
            if(error.status === 401) {
                console.log('Your Username or Password is incorrect. Please try again!');
                return <Redirect to='/login'  />;

            } else {
                console.log('Sorry! Something went wrong. Please try again!');
            }
        });
    }

    render()
    {
        return(
            <div>
                <br/>
                <br/>
                <div className="form-group mx-auto .d-block w-50" >
                    <label htmlFor="exampleInputEmail1" className="mx-auto" >Enter username or email.</label>
                    <input type="text" className="form-control mx-auto" id="exampleInputEmail1" aria-describedby="emailHelp"
                           placeholder="username or email" name="usernameOrEmail" onChange={this.handleChange} value = {this.state.usernameOrEmail}/>
                </div>
                <div className="form-group mx-auto .d-block w-50">
                    <label htmlFor="exampleInputPassword1" className="mx-auto" >Password</label>
                    <input type="password" className="form-control mx-auto" id="exampleInputPassword1" placeholder="password"
                           name="password" onChange={this.handleChange} value = {this.state.password}/>
                </div>
                <div className="form-group mx-auto .d-block w-50">
                    <button type="button" className="btn btn-secondary float-sm-right"  onClick={this.clickHandler}>Submit</button>
                </div>
            </div>

        );
    }
}

export default Login;