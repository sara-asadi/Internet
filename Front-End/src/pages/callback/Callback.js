//import "./login-styles.css";

import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;

async function callbackUser(credentials, navigate) {
  debugger
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
  debugger
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();
    let code = new URLSearchParams(window.location.search).get("code");
    callbackUser({code:code}, navigate);
    console.log(localStorage.getItem("token"));
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
