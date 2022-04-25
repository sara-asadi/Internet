import React from "react";
import MovieService from "../../services/MovieService";
import Header from "../general/Header";
import "./Movies.css";

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

  componentDidMount() {
    document.title = "Movies";
    // scrollToTop();
    if (this.state.MovieId != null) {
      // this.props.history.push("/");
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

  //   coverImage: "http://cdn.6nightmovie.click/upload/1920/-ef9_YVudPNglmyPxHHYqgSN8kmp.jpg"
  // director: "Frank Darabont"
  // duration: 142
  // id: 1
  // image: "http://cdn.6nightmovie.click/upload/280/6Mn3SsAYF9z48yqIr3eXSvVfCXVa.jpg"
  // imdbRate: 9.3
  // name: "The Shawshank Redemption ( 1994 )"
  // rating: 0
  // ratingCount: 0
  // releaseDate: "1994/10/13"
  // summary: "«اندي» (رابينز)، بانکدار محترم و پولدار ايالت نيوانگلند، به اتهام قتل همسرش و فاسق او به حبس ابد در زندان ايالتي شوشنک محکوم مي شود و اندکي بعد با «رد» (فريمن)، زنداني سياه پوست، دوست مي شود. پس از هجده سال، «اندي» ردي از قاتل اصلي پيدا مي کند و تصمیم می گیرد برای آزادی تلاش کند..."
  // writers

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
            {/*
            <div id="myCarousel" className="carousel slide" data-ride="carousel">
              <div className="carousel-inner">
                <div className="carousel-item active">
                  <div className="row">
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="Tom.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              Tom Holland
                              <br />
                              25
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="benedict.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              Benedict Cumberbatch
                              <br />
                              40
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="zendaya.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              Zendaya
                              <br />
                              25
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="jon.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              Jon Favreau
                              <br />
                              50
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="carousel-item">
                  <div className="row">
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="william.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              Jaimie Fox
                              <br />
                              40
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
                    <div className="col-sm-3">
                      <div className="container-2">
                        <a href="#">
                          <img src="jaimie.jpg" className="image-2" />
                          <div className="overlay-2">
                            <div className="text-1">
                              William Dafoe
                              <br />
                              50
                            </div>
                          </div>
                        </a>
                      </div>
                    </div>
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
            </div> */}
          </div>
        </div>

        {/* <div className="row">
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
              <p>علی</p>
              <hr />
              <div>فیلم تاثیر گذاری بود</div>
              <div className="text-left">
                <div className="vote">
                  <a href="#">
                    <img src="like.png" />
                  </a>
                  <div className="caption">12</div>
                </div>
                <div className="vote">
                  <a href="#">
                    <img src="dislike.png" />
                  </a>
                  <div className="caption">5</div>
                </div>
              </div>
            </div>
            <br />
            <div className="comment container">
              <p>حسن</p>
              <hr />
              <div>زیبا</div>
              <div className="text-left">
                <div className="vote">
                  <a href="#">
                    <img src="like.png" />
                  </a>
                  <div className="caption">5</div>
                </div>
                <div className="vote">
                  <a href="#">
                    <img src="dislike.png" />
                  </a>
                  <div className="caption">2</div>
                </div>
              </div>
            </div>
            <br />
          </div>
        </div> */}
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

  renderMovies() {
    return (
      <div>
        <div class="container">
          <div class="movies col-md-10">
            <span class="container-1">
              <a href="#">
                <img
                  src="./../images/movies/The Godfather.jpg"
                  class="poster image-1"
                />
                <div class="overlay-1">
                  <div class="text-1">
                    The Godfather
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>

            <span class="container-1">
              <a href="#">
                <img
                  src="./../images/movies/Se7en.jpg"
                  class="poster image-1"
                />
                <div class="overlay-1">
                  <div class="text-1">
                    Se7en
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>

            <span class="container-1">
              <a href="#">
                <img src="./../images/movies/1917.jpg" class="poster image-1" />
                <div class="overlay-1">
                  <div class="text-1">
                    1917
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>

            <span class="container-1">
              <a href="#">
                <img
                  src="./../images/movies/Fight Club.jpg"
                  class="poster image-1"
                />
                <div class="overlay-1">
                  <div class="text-1">
                    Fight Club
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>
            <span class="container-1">
              <a href="#">
                <img
                  src="./../images/movies/Forrest Gump.jpg"
                  class="poster image-1"
                />
                <div class="overlay-1">
                  <div class="text-1">
                    Forrest Gump
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>
            <span class="container-1">
              <a href="#">
                <img
                  src="./../images/movies/Incendies.jpg"
                  class="poster image-1"
                />
                <div class="overlay-1">
                  <div class="text-1">
                    Incendies
                    <br />
                    8.3
                  </div>
                </div>
              </a>
            </span>
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

  // renderMain() {
  //     return (
  //         <main>
  //             <div className="sub-header-red">
  //                 <div className="sub-header-over">
  //                     <img alt="" className="sub-header-over-img"
  //                          src={this.state.Movie.logo}/>
  //                 </div>
  //             </div>
  //             <div className="sub-header-over-text">{this.state.Movie.name}</div>

  //             <div className="container restaurant-main-container">

  //                 <div className="row">
  //                     <div className="col-lg-4 col-6 center-text"/>
  //                     <div className="col-lg-8 col-6 center-text">
  //                 <span className="menu-text">
  //                     منوی غذا
  //                 </span>
  //                     </div>
  //                 </div>
  //                 <div className="row mt-5">

  //                     <div className="col-lg-4 col-6">
  //                         <Cart/>
  //                     </div>

  //                     <div className="col-lg-8 col-6 food-list-container">
  //                         <div className="row">

  //                             {/* {this.renderMenu()} */}

  //                         </div>
  //                     </div>
  //                 </div>
  //             </div>

  //         </main>
  //     );
  // }
}
