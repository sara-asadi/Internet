import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import React from 'react';
// import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Signup from "./pages/signup/Signup";
//import Movies from "./pages/movies/Movies";
import Actor from './pages/actor/Actor';

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
            {/*<Route path="/movies" element={<Movies />} />
        
        
        {/* <Route exact path="/" component={Home} /> */}
          </Routes>
        </Router >
        <ToastContainer />
      </div>
    );
  }
}

export default App;
