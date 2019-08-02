import React from 'react'
import {signup} from '../../util/APIUtils'
import {Redirect} from 'react-router-dom';


class Signup extends React.Component{
    constructor(props){
        super(props)
        this.state = {username:'', firstname: '', lastname: '', email: '', pass1: '', pass2:'', result: '', message: ''}
        this.handleChange = this.handleChange.bind(this)
        this.clickHandler = this.clickHandler.bind(this)
    }

    handleChange(event){
        this.setState({[event.target.name]: event.target.value})
    }

    clickHandler(){
        const signUpRequest = {
            name: this.state.firstname,
            username: this.state.username,
            lastName: this.state.lastname,
            email: this.state.email,
            password: this.state.pass1
        }
        signup(signUpRequest)
            .then(response => {
                console.log("r- "+ response);
                this.setState({message: 'ok'});
            }).catch(error => {
            console.log("e- "+ error);
            this.setState( {message: 'Sorry! Something went wrong. Please try again!'})
        });

    }

    render()
    {
        console.log(" " +this.state.message)

        if(this.state.message == 'ok')
            return  <Redirect to='/login'  />;

        if(this.props.isAuthenticated){
            return <Redirect to='/movies'  />;
        }
        var style = {
            width: "50%",
            display: "block"
        }
        return(
            <div className="container" >
                <div className="form-group">
                    <label htmlFor="exampleInput2" className="mx-auto" style={style}>Username</label>
                    <input type="text" className="form-control mx-auto" style={style} id="exampleInput2" aria-describedby="nameHelp"
                           placeholder="Enter username" name="username" onChange={this.handleChange} value = {this.state.username}/>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleInput" className="mx-auto" style={style}>First Name</label>
                    <input type="text" className="form-control mx-auto" style={style} id="exampleInput" aria-describedby="nameHelp"
                           placeholder="Enter first name" name="firstname" onChange={this.handleChange} value = {this.state.firstname}/>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleInput1" className="mx-auto" style={style}>Last Name</label>
                    <input type="text" className="form-control mx-auto" style={style} id="exampleInput1" aria-describedby="lastNameHelp"
                           placeholder="Enter lastname" name="lastname" onChange={this.handleChange} value = {this.state.lastname}/>
                </div>

                <div className="form-group">
                    <label htmlFor="exampleInputEmail1" className="mx-auto" style={style}>Email address</label>
                    <input type="email" className="form-control mx-auto" style={style} id="exampleInputEmail1" aria-describedby="emailHelp"
                           placeholder="Enter email" name="email" onChange={this.handleChange} value = {this.state.email}/>
                    <small style={style} id="emailHelp" className="form-text text-muted mx-auto">We'll never share your email with anyone
                        else.
                    </small>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleInputPassword1" className="mx-auto" style={style}>Password</label>
                    <input type="password" className="form-control mx-auto" style={style} id="exampleInputPassword1" placeholder="Password"
                           name="pass1" onChange={this.handleChange} value = {this.state.pass1}/>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleInputPassword1" className="mx-auto" style={style}>Passwor2</label>
                    <input type="password" className="form-control mx-auto"style={style} id="exampleInputPassword2" placeholder="Password Again"
                           name="pass2" onChange={this.handleChange} value = {this.state.pass2}/>
                </div>
                <div className="form-group mx-auto .d-block w-50">
                    <button type="button" className="btn btn-secondary float-sm-right"  onClick={this.clickHandler}>Submit</button>
                </div>
            </div>

        )
    }
}

export default Signup;