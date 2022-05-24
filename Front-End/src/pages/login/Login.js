import "./login-styles.css";

import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;

async function loginUser(credentials, navigate) {
  console.log('loginUser');
  axios.post('http://localhost:8080/login',
    { "email": credentials.email, "password": credentials.password },
    {
      "Content-Type": "application/json"
    }
  ).then(response => {
    let token = response.data.token;
    //console.log('token:' + token);
    //console.log('response:' + JSON.stringify(response));
    if (response.status === 200) {
      console.log('login 200');
      if (response.data === "password incorrect") {
        console.log("password incorrect!");
        toast.error("password incorrect!");
      } else {
        toast.success("You Logged in!");
        localStorage.setItem("token", JSON.stringify(token));
        //console.log("token" + localStorage.getItem("token"));
        navigate("/");
      }
    } else {
      console.log('errror');
      toast.error(response.data.message);
    }

  }).catch(function (error) { console.log("error :", error); });
}

export default function Login({ setJWT }) {

  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    await loginUser({
      email: email,
      password: password,
    }, navigate);
  }

  if (localStorage.getItem("token") !== null) {
    //localStorage.removeItem("token");
    console.log("token" + localStorage.getItem("token"));
    return <Navigate to="/" />;
  } else {
    return (
      <div className="container-login" dir="ltr">
        <div className="container p-1">
          <h1>Login</h1>
          <br></br>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Email:</label>
              <input type="email" className="form-control" onChange={e => setEmail(e.target.value)} placeholder="email" required />
            </div>
            <br></br>
            <div className="form-group">
              <label>Password:</label>
              <input type="password" className="form-control" onChange={e => setPassword(e.target.value)} placeholder="password" required />
            </div>
            <br></br>
            <button type="submit" className="form-button btn btn-success">Login</button>
            <br></br><br></br><hr></hr><br></br>
            <Link to='/signup'><button type="submit" className="btn btn-primary">Signup</button></Link>
            <br/>
            <a href="https://github.com/login/oauth/authorize?client_id=5e729b70c5d14691a84d&scope=user" className="btn btn-primary">login With GitHub</a>
          </form>
        </div>
      </div>
    );
  }
}
