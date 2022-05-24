import * as React from "react";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { useState } from 'react';

const axios = require('axios').default;


function callbackUser(credentials, navigate) {
  axios.post('http://localhost:8080/callback',
    { "code": credentials.code },
    {
      "Content-Type": "application/json"
    }
  ).then(response => {
    let token = response.data.token;
    if (response.status === 200) {
      console.log('callback 200');
      toast.success("You Logged in!");
      localStorage.setItem("token", token);
      console.log(token);
      navigate("/");
    } else {
      toast.error(response.data.message);
    }
  }).catch(function (error) { });
}

export default function Callback({ setJWT }) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();
  let code = new URLSearchParams(window.location.search).get("code");

  callbackUser({ code: code }, navigate);
  console.log(localStorage.getItem("token"));

  if (localStorage.getItem("token") === null) {
    return (
      <div className="loading" >
        <div className="spinner-grow" role="status">
        </div>
      </div>
    );
  }
  else {
    return <Navigate to="/" />;
  }
}
