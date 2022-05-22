import * as React from "react";
import { Link, Navigate } from "react-router-dom";


export default function Logout() {
    localStorage.removeItem("token");
    console.log("logout");
    return <Navigate to='/login' />;
}
