import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import React from 'react';

import Login from "./pages/login/Login";
import Signup from "./pages/signup/Signup";
import Movies from "./pages/movies/Movies";
import Actor from './pages/actor/Actor';
import Logout from './pages/Logout';
import Callback from './pages/callback/Callback';


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      jwt: null
    };
  }

  render() {
    const loadingTag = () => {
      if (this.state.loading) {
        return (
          <div className="loading" >
            <div className="spinner-grow" role="status">
            </div>
          </div>);
      }
      else {
        return null;
      }
    }

    const setLoading = (v) => {
      this.setState({ loading: v });
    }

    return (
      <div className="wrapper">
        {loadingTag()}
        <Router>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/actors" element={<Actor />} />
            <Route exact path="/" element={<Movies />} />
            <Route path="/movies" element={<Movies />} />
            <Route path="/logout" element={<Logout />} />
            <Route path="/callback" element={<Callback />} />
          </Routes>
        </Router >
        <ToastContainer />
      </div>
    );
  }
}

export default App;
