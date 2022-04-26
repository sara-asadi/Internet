import "../../Assets/styles/signup-styles.css";

import * as React from "react";
import {Link} from "react-router-dom";

export default class Signup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    componentDidMount() {
        document.title = "Signup";
    }

    render() {
        return (
        <div class="container-login" dir="ltr">
        <div class="container p-1">
          <h1>Signup</h1>
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
            <div class="form-group">
              <label for="name">Name:</label>
              <input
                type="name"
                class="form-control"
                id="name"
                placeholder="Enter name"
                name="name"
              />
            </div>
            <br></br>
            <div class="form-group">
              <label for="age">Age:</label>
              <input
                type="age"
                class="form-control"
                id="age"
                placeholder="Enter age"
                name="age"
              />
            </div>
            <br></br>
            <button type="submit" class="btn btn-success">
            Signup
            </button>
          </form>
        </div>
      </div>
        );
    }

}