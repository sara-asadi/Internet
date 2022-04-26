import "../../Assets/styles/login-styles.css";
import * as React from "react";
import { Link } from "react-router-dom";

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    document.title = "Login";
  }

  render() {
    return (
      <div class="container-login" dir="ltr">
        <div class="container p-1">
          <h1>Login</h1>
          <br></br>
          <form action="" method="POST">
            <div class="form-group">
              <label for="email">Email:</label>
              <input
                type="email"
                class="form-control"
                id="email"
                placeholder="Enter email"
                name="email"
              />
            </div>
            <br></br>
            <div class="form-group">
              <label for="pwd">Password:</label>
              <input
                type="password"
                class="form-control"
                id="pwd"
                placeholder="Enter password"
                name="password"
              />
            </div>
            <br></br>
            <button type="submit" class="btn btn-success">
              Login
            </button>
          </form>
        </div>
      </div>
    );
  }
}
