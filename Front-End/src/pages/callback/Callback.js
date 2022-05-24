//import "./login-styles.css";

import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;

async function callbackUser(credentials, navigate) {
  console.log("hi");
  axios.post('http://localhost:8080/callback',
    { "code":credentials.code},
    {
      "Content-Type": "application/json"
    }
  ).then(response => {
    let token = response.data.token;
    console.log('token:' + token);
    console.log('response:' + JSON.stringify(response));
    if (response.status === 200) {
      console.log('login 200');
      toast.success("You Logged in!");
      localStorage.setItem("token", JSON.stringify(token));
        //console.log("token" + localStorage.getItem("token"));
      navigate("/");
    } else {
      console.log('errror');
      toast.error(response.data.message);
    }

  }).catch(function (error) { console.log("error :", error); });
}

export default function Callback({ setJWT }) {

  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();

//  const handleSubmit = async e => {
//    e.preventDefault();
//    await loginUser({
//      email: email,
//      password: password,
//    }, navigate);
//  }

  if (localStorage.getItem("token") !== null) {
    //localStorage.removeItem("token");
    console.log("token" + localStorage.getItem("token"));
    return <Navigate to="/" />;
  } else {
    return (
      <div className="loading" >
        <div className="spinner-grow" role="status">
        </div>
      </div>
    );
  }
}
