
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
// import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Signup from "./pages/signup/Signup";
// import Movies from "./pages/movies/Movies";

function App() {
  return (
    <Router>
    <Routes>
      {/* <Route path="/movies" component={Movies}/> */}
      {/* <Route path="/dashboard">
        <Dashboard />
      </Route>
      <Route path="/profile">
        <Profile />
      </Route>
      <Route path="/restaurant" component={Restaurant} /> */}
      <Route path="/login" element={<Login />}/>
        {/* <Logout/> */}
      {/* </Route> */}
      <Route path="/signup" element={<Signup />}/>
      {/* <Route path="/test">
        <Example/>
      </Route> */}
      {/* <Route exact path="/" component={Home} /> */}
    </Routes>
  </Router>
  );
}

function Logout(){
  return (<div>
    HI
    </div>);
}

export default App;
