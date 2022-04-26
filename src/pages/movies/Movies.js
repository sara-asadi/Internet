import React from "react";
import MovieService from "../../services/MovieService";
import Header from "../general/Header";
import ActorCard from "./ActorCard";
import CommentCard from "./CommentCard";
import MovieCard from "./MovieCard";
import "./Movies.css";

export default class Movies extends React.Component {
  constructor(props) {
    super(props);
    debugger
    this.state = {
      queryParams: new URLSearchParams(window.location.search),
      MovieId: new URLSearchParams(window.location.search).get("id"),
      Movie: null,
      notFound: false,
    };
  }

  componentDidMount() {
    document.title = "Movies";
    // scrollToTop();
    if (this.state.MovieId != null) {
      this.getMovieById(this.state.MovieId);
    }
  }

  async getMovieById(id) {
    debugger;
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

  renderMovie() {
    return (
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
                  {/* <img width="15%" src="star-icon.png" /> */}
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
                  نویسنده: Stan Lee, Chris McKenna, Steve Ditko
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
                <img width="150%" src="spiderman-1.jpg" />
              </div>
              <div className="col-sm-2"></div>
            </div>
          </div>
        </div>

        <div className="row">
          <div className="col-sm-1"></div>
          <div className="col-sm-8 cast container">
            <p>بازیگران</p>
            <div className="actors carousel slide" data-ride="carousel">
              <div className="carousel-inner">
                <div className="carousel-item active">
                  <div className="row">
                    {this.actorsList(this.state.MovieId)}
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

        <div className="row">
          <div className="col-sm-1"></div>
          <div className="col-sm-8 cast container">
            <p>دیدگاه ها</p>
            <div className="comment container">
              <p>دیدگاه خود را اضافه کنید:</p>
              <hr />
              <form>
                <div className="form-group mr-5">
                  <textarea
                    className="form-control comment"
                    rows="2"
                    id="comment"
                  ></textarea>
                </div>
              </form>
              <div className="m-2 text-left">
                <a href="#" className="btn btn-success" role="button">
                  ثبت
                </a>
              </div>
            </div>
            <br />
            <div className="comment container">
              {this.commentsList(this.state.MovieId)}
            </div>
          </div>
        </div>
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

  async actorsList(id) {
    debugger;
    let actorsList = await MovieService.getActorsById(id);
    return actorsList.map((actor, i) => {
      debugger;
      return <ActorCard Actor={actor} key={"A" + i} />;
    });
  }

  async commentsList(id){
    let commentsList = await MovieService.getCommentsById(id);
    return commentsList.map((comment, i)=>{
      return <CommentCard Comment={comment} key={"C"+i}/>;
    })
  }

  async moviesList(){
    let movies = await MovieService.getMovies();
    return movies.map((movie, i)=>{
      return <MovieCard Movie={movie} key={"M"+i}/>;
    })
  }

  renderMovies() {
    return (
      <div>
        <div class="container">
          <div class="movies col-md-10">
           {this.moviesList()}

          </div>
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
