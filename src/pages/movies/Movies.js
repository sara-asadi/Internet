import React from "react";
import { MOVIESCAST_URL, MOVIESCOMMENTS_URL, MOVIES_URL } from "../../config/config";
import MovieService from "../../services/MovieService";
import Header from "../general/Header";
import ActorCard from "./ActorCard";
import CommentCard from "./CommentCard";
import MovieCard from "./MovieCard";
import "./Movies.css";
import "../Style.css";

export default class Movies extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      queryParams: new URLSearchParams(window.location.search),
      MovieId: new URLSearchParams(window.location.search).get("id"),
      Movie: null,
      notFound: false,
    };
  }

  async componentDidMount() {
    document.title = "Movies";

    if (this.state.MovieId != null) {
      this.getMovieById(this.state.MovieId);
      const apiUrl2 = `${MOVIESCAST_URL}${this.state.MovieId}`;
      const response2 = await fetch(apiUrl2);
      const json2 = await response2.json();
      debugger
      setTimeout(() => {
        this.setState({
          Actors: json2
        });
      }, 2000);
      const apiUrl1 = `${MOVIESCOMMENTS_URL}${this.state.MovieId}`;
      const response1 = await fetch(apiUrl1);
      const json1 = await response1.json();
      debugger
      setTimeout(() => {
        this.setState({
          Comments: json1
        });
      }, 2000);
    }
    else{
      let searchFilter = JSON.parse(localStorage.getItem("searchFilter"));
      let typeFilter = JSON.parse(localStorage.getItem("typeFilter"));
      if (searchFilter == null) searchFilter = "";
      if (typeFilter == null) typeFilter = "all";
      const apiUrl = `${MOVIES_URL}?search=${searchFilter}&type=${typeFilter}`;
      const response = await fetch(apiUrl);
      const json = await response.json();
      debugger
      setTimeout(() => {
        this.setState({
          Movies: json,
        });
      }, 2000);
    }
    debugger;
  }

  getId = () => {
    return JSON.parse(localStorage.getItem("id"));
  };

  async getMovieById(id) {
    let Movie = await MovieService.getMovieById(id);
    if (Movie == null) {
      this.setState({
        notFound: true,
      });
    } else {
      this.setState({
        Movie: Movie,
      });
      document.title = Movie.name;
    }
  }

  render() {
    return (
      <div className="h-100">
        <Header />
        {this.state.MovieId !== null
          ? this.state.Movie !== null
            ? this.renderMovie()
            : this.renderLoading()
          : this.renderMovies()}
      </div>
    );
  }
  async AddToWatchList(){
    this._isMounted && this.setState({isLoading: true});
        await MovieService.AddToWatchList(this.state.Movie.id);
        this._isMounted && this.setState({isLoading: false});

  }
  renderMovie() {
    return (
      <div>
      <div>
        <div
          className="hero-image"
          style={{ backgroundImage: `url(${this.state.Movie.coverImage})` }}
        >
          <div className="hero-text text-right">
            <div className="row">
              <div className="col-sm-1"></div>
              <div className="col-sm-2">
                <div className="imdb-rate">
                  {this.state.Movie.imdbRate}
                  <br />
                  <img width="15%" src={"../../Assets/images/star-icon.png"} />
                  <div className="row">
                    <div className="col user-rate">
                      امتیاز کاربران <br /> ({this.state.Movie.ratingCount})
                    </div>
                    <div className="col user-rate-val">
                      {this.state.Movie.rating}
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-sm-5 text-right">
                <p id="name" className="text-left">
                  {this.state.Movie.name}
                </p>
                <p id="director">کارگردان: {this.state.Movie.director}</p>
                <p id="writers">
                  نویسندگان: {this.state.Movie.writers}
                </p>
                <p id="duration">مدت زمان: {this.state.Movie.duration}</p>
                <br />
                <p id="releaseDate" className="text-left">
                  تاریخ انتشار: {this.state.Movie.releaseDate}
                </p>
                <hr />
                <p className="text-left">{this.state.Movie.summary}</p>
              </div>
              <div className="col-sm-2">
                <img width="150%" src={this.state.Movie.image} />
                <br></br>
                {/*<Link to={this.AddToWatchList}>*/}
                  <button className="button-2">add to watchlist</button>
                 {/* </Link>*/}
                
              </div>
              <div className="col-sm-2"></div>
            </div>
          </div>
        </div>
<br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br>
         <div className="row">
          <div className="col-sm-1"></div>
          <div className="col-sm-8 cast container">
            <p>بازیگران</p>
            <div className="actors carousel slide" data-ride="carousel">
              <div className="carousel-inner">
                <div className="carousel-item active">
                  <div className="row">
                    <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:4277/actors?id="+this.state.Movie.actorIds[0]} ><img src={this.state.Movie.actorImages[0]} className="image-2"/><div className="overlay-2"><div className="text-1">{this.state.Movie.actorNames[0]}<br></br>{this.state.Movie.actorAges[0]}</div></div></a></div></div>
                      <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:4277/actors?id="+this.state.Movie.actorIds[1]}><img src={this.state.Movie.actorImages[1]} className="image-2"/><div className="overlay-2"><div className="text-1">{this.state.Movie.actorNames[1]}<br></br>{this.state.Movie.actorAges[1]}</div></div></a></div></div>
                      <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:4277/actors?id="+this.state.Movie.actorIds[2]}><img src={this.state.Movie.actorImages[2]} className="image-2"/><div className="overlay-2"><div className="text-1">{this.state.Movie.actorNames[2]}<br></br>{this.state.Movie.actorAges[2]}</div></div></a></div></div>
                      <div className="col-sm-3"><div className="container-2"><a href={"http://localhost:4277/actors?id="+this.state.Movie.actorIds[3]}><img src={this.state.Movie.actorImages[3]} className="image-2"/><div className="overlay-2"><div className="text-1">{this.state.Movie.actorNames[3]}<br></br>{this.state.Movie.actorAges[3]}</div></div></a></div></div>
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
              <hr /><form> <div className="form-group mr-5"><textarea  className="form-control comment" rows="2" id="comment"></textarea></div>  </form>
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
    if(this.state.Actors){
      const actors = this.state.Actors;
      debugger;

      var items = [];
      for(var i=0 ; i<actors.length; i++){
        items.push(<ActorCard Actor={actors[i]} key={"M"+i}/>);
      }
      return items;
    }
    return undefined
  }

  commentsList() {
    if(this.state.Comments){

      const comments = this.state.Comments;
      debugger;
      var items = [];
      debugger
      for(var i=0 ; i<comments.length; i++){
        items.push(<CommentCard Comment={comments[i]} key={"M"+i}/>);
      }
      return items;
    }
    return undefined
  }

  moviesList() {
    if(this.state.Movies){

      const movies = this.state.Movies.slice(0,10);
      var items = [];
      debugger
      for(var i=0 ; i<10; i++){
        items.push(<MovieCard Movie={movies[i]} key={"M"+i}/>);
      }
      debugger
      return items;
    }
    return undefined
  }

  renderMovies() {
    return (
      <div>
        <div class="container">
          <div class="movies col-md-10">{this.moviesList()}</div>
          <span class="col-md-2 sort-bar">
            <label class="title">:رتبه بندی بر اساس</label>
            <div class="sort">
              <form action="movies" method="POST" class="sort-form">
                <button
                  type="submit"
                  name="action"
                  value="sort_by_date"
                  class="btn btn-link"
                >
                  تاریخ
                </button>
                <button
                  type="submit"
                  name="action"
                  value="sort_by_imdb"
                  class="btn btn-link"
                >
                  امتیاز imdb
                </button>
              </form>
            </div>
          </span>
        </div>
      </div>
    );
  }
}
