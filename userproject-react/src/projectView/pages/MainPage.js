import React from 'react';
import {Link} from 'react-router-dom'

class MainPage extends React.Component{
    constructor(props){
        super(props)
    }

    render(){
        return(
            <div className="jumbotron">
                <h1 className="display-4">Hello, this is MovieNotepad!</h1>
                <p className="lead">With this site you can watch movies online, make your own list and see what others watching. </p>
                <hr className="my-4"/>
                    <p>If you want to explore, you can start in a minute by only clicking register. If you are already logged in you can search any movie.</p>
                    <Link className="btn btn-primary btn-lg" to="/signup" role="button">Register</Link>
            </div>
        )
    }
}

export default MainPage;