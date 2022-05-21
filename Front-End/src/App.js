
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
// import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Signup from "./pages/signup/Signup";
//import Movies from "./pages/movies/Movies";
import Actor from './pages/actor/Actor';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/actors" element={<Actor />} />
        {/*<Route path="/movies" element={<Movies />} />
        
        <Route path="/signup" element={<Signup />} />
        {/* <Route exact path="/" component={Home} /> */}
      </Routes>
    </Router>
  );
}

// function Logout(){
//   return (<div>
//     HI
//     </div>);
// }

export default App;
