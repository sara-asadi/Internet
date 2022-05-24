import React from "react";
import { Navigate } from "react-router-dom";
import MovieService from "../../services/MovieService";
import Header from "../general/Header";
import ActorCard from "./ActorCard";
import CommentCard from "./CommentCard";
import MovieCard from "./MovieCard";
import "../../assets/styles/Movies.css";
import "../../assets/styles/Style.css";


import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

const axios = require('axios').default;
var first = true;

export default class Movies extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      queryParams: new URLSearchParams(window.location.search),
      id: new URLSearchParams(window.location.search).get("id"),
      movie: null,
      movies: []
    };
  }

  componentDidMount() {
    console.log("movies");
    document.title = "movies";
    if (this.state.id != null) {
      this.getMovieById(this.state.id);
      /*
      const apiUrl2 = `${MOVIESCAST_URL}${this.state.id}`;
      const response2 = fetch(apiUrl2);
      const json2 = response2.json();

      setTimeout(() => {
        this.setState({
          Actors: json2
        });
      }, 2000);
      const apiUrl1 = `${MOVIESCOMMENTS_URL}${this.state.id}`;
      const response1 = fetch(apiUrl1);
      const json1 = response1.json();

      setTimeout(() => {
        this.setState({
          Comments: json1
        });
      }, 2000);
      */
    }
    else {
      let json = this.getMovies();
      /*
      let searchFilter = JSON.parse(localStorage.getItem("searchFilter"));
      let typeFilter = JSON.parse(localStorage.getItem("typeFilter"));
      if (searchFilter == null) searchFilter = "";
      if (typeFilter == null) typeFilter = "all";
      const apiUrl = `${MOVIES_URL}?search=${searchFilter}&type=${typeFilter}`;
      const response = fetch(apiUrl);
      const json = response.json();

      setTimeout(() => {
        this.setState({
          Movies: json,
        });
      }, 2000);*/
    }
  }

  getId = () => {
    return JSON.parse(localStorage.getItem("id"));
  };

  getMovies() {
    const getData = () => {
      const token = localStorage.getItem("token");
      axios.get('http://localhost:8080/movies', {
        headers: {
          "Authorization": `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }).then(response => {
        console.log(response);
        this.setState({ movies: response.data });

      }).catch(function (error) {
        if (error) {
          console.log(error);
        }
      });
    }

    if (first) {
      first = false;
      getData();
    }
  }

  getMovieById(id) {
    const getData = () => {
      const token = localStorage.getItem("token");
      console.log("token" + token);
      axios.get('http://localhost:8080/movies/' + id, {
        headers: {
          "Authorization": `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }).then(response => {
        console.log("response:");
        console.log(response);
        this.setState({ movie: response.data });
        document.title = response.data.name;
      }).catch(function (error) {
        if (error) {
          console.log(error);
          toast.error("error 404: Not Found!");
        }
      });
    };
    if (first) {
      first = false;
      getData();
    }
  }

  render() {
    if (localStorage.getItem("token") === null) {
      return <Navigate to="/login" />
    }
    return (
      <div className="h-100">
        <Header />
        {this.state.id !== null
          ? this.state.movie !== null
            ? this.renderMovie()
            : this.renderLoading()
          : this.renderMovies()}
      </div>
    );
  }

  AddToWatchList() {
    this._isMounted && this.setState({ isLoading: true });
    MovieService.AddToWatchList(this.state.movie.id);
    this._isMounted && this.setState({ isLoading: false });
  }

  renderMovie() {
    return (
      <div>
        <div>
          <div
            className="hero-image"
            style={{ backgroundImage: `url(${this.state.movie.coverImage})` }}
          >
            <div className="hero-text text-right">
              <div className="row">
                <div className="col-sm-1"></div>
                <div className="col-sm-2">
                  <div className="imdb-rate">
                    {this.state.movie.imdbRate}
                    <br />
                    <img width="15%" src={"../../assets/images/star-icon.png"} />
                    <div className="row">
                      <div className="col user-rate">
                        امتیاز کاربران <br /> ({this.state.movie.ratingCount})
                      </div>
                      <div className="col user-rate-val">
                        {this.state.movie.rating}
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-sm-5 text-right">
                  <p id="name" className="text-left">
                    {this.state.movie.name}
                  </p>
                  <p id="director">کارگردان: {this.state.movie.director}</p>
                  <p id="writers">
                    نویسندگان: {this.state.movie.writers}
                  </p>
                  <p id="duration">مدت زمان: {this.state.movie.duration}</p>
                  <br />
                  <p id="releaseDate" className="text-left">
                    تاریخ انتشار: {this.state.movie.releaseDate}
                  </p>
                  <hr />
                  <p className="text-left">{this.state.movie.summary}</p>
                </div>
                <div className="col-sm-2">
                  <img width="150%" src={this.state.movie.image} />
                  <br></br>
                  {/*<Link to={this.AddToWatchList}>*/}
                  <button className="button-2">add to watchlist</button>
                  {/* </Link>*/}

                </div>
                <div className="col-sm-2"></div>
              </div>
            </div>
          </div>
          <br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br>
          <div className="row">
            <div className="col-sm-1"></div>
            <div className="col-sm-8 cast container">
              <p>بازیگران</p>
              <div className="actors carousel slide" data-ride="carousel">
                <div className="carousel-inner">
                  <div className="carousel-item active">
                    <div className="row">
                      <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:3000/actors?id=" + 465} ><img src={this.state.movie.image} className="image-2" /><div className="overlay-2"><div className="text-1"></div></div></a></div></div>
                      <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:3000/actors?id=" + 72}><img src={this.state.movie.image} className="image-2" /><div className="overlay-2"><div className="text-1"></div></div></a></div></div>
                    </div>
                  </div>
                </div>
                <a
                  className="carousel-control-prev"
                  href="#myCarousel"
                  data-slide="prev"
                ></a>
                <a
                  className="carousel-control-next"
                  href="#myCarousel"
                  data-slide="next"
                ></a>
              </div>
            </div>
          </div>
        </div>
        <br></br><br></br><br></br><br></br><br></br>
        <div className="row">
          <div className="col-sm-1"></div>
          <div className="col-sm-8 cast container"><p>دیدگاه ها</p>
            <div className="comment container"> <p>دیدگاه خود را اضافه کنید:</p>
              <hr /><form> <div className="form-group mr-5"><textarea className="form-control comment" rows="2" id="comment"></textarea></div>  </form>
              <div className="m-2 text-left"><a href="#" className="btn btn-success" role="button"> ثبت </a></div>
            </div>
            <br />
            <div className="comment container">   {this.commentsList()} </div>
          </div>
        </div>
        <br></br><br></br><br></br><br></br><br></br>

      </div>

    );
  }

  renderLoading() {
    return (
      <div className="h-100 m-5 center-text p-5">
        <div className="spinner-grow text-danger m-5" role="status">
          <span className="sr-only">Loading...</span>
        </div>
      </div>
    );
  }

  actorsList() {
    if (this.state.Actors) {
      const actors = this.state.Actors;

      var items = [];
      for (var i = 0; i < actors.length; i++) {
        items.push(<ActorCard Actor={actors[i]} key={"M" + i} />);
      }
      return items;
    }
    return undefined
  }

  commentsList() {
    if (this.state.Comments) {

      const comments = this.state.Comments;
      var items = [];
      for (var i = 0; i < comments.length; i++) {
        items.push(<CommentCard Comment={comments[i]} key={"M" + i} />);
      }
      return items;
    }
    return undefined
  }

  moviesList() {
    if (this.state.movies.length !== 0) {
      var items = [];
      for (var i = 0; i < 20; i++) {
        items.push(<MovieCard movie={this.state.movies[i]} key={"M" + i} />);
      }
      return items;
    }
    return undefined
  }

  renderMovies() {
    if (this.state.movies === []) {
      this.getMovies();
    }
    return (
      <div>
        <span className="col-md-2 sort-bar">
          <label className="title">:رتبه بندی بر اساس</label>
          <div className="sort">
            <form action="movies" method="POST" className="sort-form">
              <button
                type="submit"
                name="action"
                value="sort_by_date"
                className="btn btn-link"
              >
                تاریخ
              </button>
              <button
                type="submit"
                name="action"
                value="sort_by_imdb"
                className="btn btn-link"
              >
                امتیاز imdb
              </button>
            </form>
          </div>
        </span>
        <br></br><br></br><br></br>
        <div className="movies">{this.moviesList()}</div>

      </div>
    );
  }
}
