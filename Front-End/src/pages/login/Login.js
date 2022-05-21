import "./login-styles.css";

import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;

async function loginUser(credentials, navigate) {
  console.log('loginUser');
  let token = axios.post('http://localhost:8080/login',
    { "email": credentials.email, "password": credentials.password },
    {
      "Authorization": `Bearer ${localStorage.getItem("token")}`,
      "Content-Type": "application/json"
    }
  ).then(response => {
    console.log('token:' + token);
    console.log(response);

    if (response.status === 200) {
      console.log('200');
      toast.success("You Logged in!");
      localStorage.setItem("token", response.data.token);
      navigate("/");
    } else {
      console.log('errror');
      toast.error(response.data.message);
    }

  }).catch(function (error) { console.log("error :", error); });

  return token;
}

export default function Login({ setJWT }) {

  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    const token = await loginUser({
      email: email,
      password: password,
    }, navigate);
    if (email && token) {
      localStorage.setItem("token", token);
      navigate("/");
    }

  }

  if (localStorage.getItem("token") !== null) {
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
          </form>
        </div>
      </div>
    );
  }
}
