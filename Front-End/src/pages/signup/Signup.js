import "./signup-styles.css";

import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;

async function signup(credentials, navigate) {

  axios.post('http://localhost:8080/signup',
    {
      "email": credentials.email,
      "password": credentials.password,
      "nickname": credentials.name,
      "name": credentials.secondName,
      "birthDate": credentials.birthDate
    },
    {
      "Content-Type": "application/json"
    })
    .then(response => {
      //console.log(response);
      if (response.status === 200) {
        console.log('signup 200');
        if (response.data === "email already exists") {
          console.log("email already exists!");
          toast.error("email already exists!");
          return false;
        } else {
          toast.success("You Signed Up!");
          localStorage.setItem("token", response.data.token);
          navigate("/");
          return true;
        }
      }
      else {
        console.log('errror');
        toast.error(response.data.message);
        return false;
      }
    }).catch(function (error) {
      console.log("error :", error);
      return false;
    });

}

export default function Signup() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [nickname, setNickname] = useState();
  const [name, setName] = useState();
  const [birthDate, setBirthDate] = useState();
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    await signup({
      email: email,
      password: password,
      name: name,
      nickname: nickname,
      birthDate: birthDate,
    }, navigate);
  }

  if (localStorage.getItem("token") !== null) {
    return <Navigate to="/" />;
  } else {
    return (
      <div className="container-login" dir="ltr">
        <div className="container p-1">
          <h1>Signup</h1>
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
            <div className="form-group">
              <label>Name:</label>
              <input type="text" className="form-control" onChange={e => setName(e.target.value)} placeholder="name" required />
            </div>
            <br></br>
            <div className="form-group">
              <label>Nickname:</label>
              <input type="text" className="form-control" onChange={e => setNickname(e.target.value)} placeholder="nickname" required />
            </div>
            <br></br>
            <div className="form-group">
              <label>BirthDate:</label>
              <input type="date" className="form-control" onChange={e => setBirthDate(e.target.value)} required />
            </div>
            <br></br>
            <button type="submit" className="btn btn-success">
              Signup
            </button>
            <br></br><br></br><hr></hr><br></br>
            <Link to='/login'><button type="submit" className="btn btn-primary">Login</button></Link>
          </form>
        </div>
      </div>
    );
  }

}