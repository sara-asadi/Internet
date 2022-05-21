import "./login-styles.css";
import API from '../../apis/api';

import * as React from "react";
import { Link } from "react-router-dom";

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
    }
    this.handleEmailChange = this.handleEmailChange.bind(this)
    this.handlePasswordChange = this.handlePasswordChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleEmailChange(event) {
    this.setState({
      email: event.target.value
    });
  }
  handlePasswordChange(event) {
    this.setState({
      password: event.target.value
    });
  }
  handleSubmit(e) {
    console.log('submit')
    e.preventDefault();
    if (!this.state.email) {
      return
    }
    API.post('auth/login/', {
      email: this.state.email,
      password: this.state.password
    }).then((resp) => {
      if (resp.status === 200) {
        console.log(resp.data);
        window.location.href = "http://localhost:3000/"
      }
    }).catch(error => {
      console.log('error in submit')
    })
  }
  componentDidMount() {
    document.title = "Login";
  }

  render() {
    return (
      <div className="container-login" dir="ltr">
        <div className="container p-1">
          <h1>Login</h1>
          <br></br>
          <form onSubmit={this.handleSubmit}>
            <div className="form-group">
              <label>Email:</label>
              <input type="email" className="form-control" onChange={this.handleEmailChange} placeholder="email" required="" />
            </div>
            <br></br>
            <div className="form-group">
              <label>Password:</label>
              <input type="password" className="form-control" onChange={this.handlePasswordChange} placeholder="password" required="" />
            </div>
            <br></br>
            <button type="submit" class="form-button btn btn-success">Login</button>
            {this.state.isLoading &&
              <span className="spinner-border mr-2" role="status" aria-hidden="true" />
            }

            <Link to='/signup'><button type="submit" class="btn btn-success">Signup</button></Link>
          </form>
        </div>
      </div>
    );
  }
}
