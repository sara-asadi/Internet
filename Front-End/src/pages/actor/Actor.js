import React from "react";
import ActorService from "../../services/ActorService";
import Header from "../general/Header";
import "../../assets/styles/Style.css"

export default class Actor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      queryParams: new URLSearchParams(window.location.search),
      ActorId: new URLSearchParams(window.location.search).get("id"),
      Actor: null,
      notFound: false,
    };
    debugger
  }
  componentDidMount() {
    document.title = "Actor";
    if (this.state.ActorId != null) {
      this.getActorById(this.state.ActorId);
    }
  }

  async getActorById(id) {
    let Actor = await ActorService.getActorById(id);
    debugger;
    if (Actor == null) {
      this.setState({
        notFound: true,
      });
    } else {
      this.setState({
        Actor: Actor,
      });
      document.title = Actor.name;
    }

  }

  render() {
    return (
      <div className="h-100">
        <Header />
        hi
        {this.state.Actor !== null
          ? this.renderActor()
          : this.renderLoading()}
      </div>
    );
  }

  renderActor() {
    return (
      <div><div class="row">
        <div class="col-sm-8">
          <div class="actor-desc-text text-center">مشخصات بازیگر</div>
          <div class="actor-desc-text text-right">نام: {this.state.Actor.name}</div>
          <div class="actor-desc-text text-right">تاریخ تولد: {this.state.Actor.birthDate}</div>
          <div class="actor-desc-text text-right">ملیت: {this.state.Actor.nationality}</div>
          <div class="actor-desc-text text-right">تعداد فیلم ها: {this.state.Actor.movieCount}</div>
          <div class="actor-desc-text text-center">فیلم ها</div>
          <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-10 actor-movies container">
              <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                  <div class="carousel-item active"><div class="row">
                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[0]}><img src={this.state.Actor.movieImages[0]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[0]}<br />{this.state.Actor.movieRatings[0]}</div></div></a>
                    </div></div>

                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[1]}><img src={this.state.Actor.movieImages[1]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[1]}<br />{this.state.Actor.movieRatings[1]}</div></div></a>
                    </div></div>

                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[2]}><img src={this.state.Actor.movieImages[2]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[2]}<br />{this.state.Actor.movieRatings[2]}</div></div></a>
                    </div></div>
                  </div></div>

                  <div class="carousel-item"><div class="row">
                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[3]}><img src={this.state.Actor.movieImages[3]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[3]}<br />{this.state.Actor.movieRatings[3]}</div></div></a>
                    </div></div>

                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[4]}><img src={this.state.Actor.movieImages[4]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[4]}<br />{this.state.Actor.movieRatings[4]}</div></div></a>
                    </div></div>

                    <div class="col-sm-4"><div class="container-1">
                      <a href={"http://localhost:4277/movies?id=" + this.state.Actor.movieIds[5]}><img src={this.state.Actor.movieImages[5]} class="image-1" />
                        <div class="overlay-1"><div class="text-1">{this.state.Actor.movieNames[5]}<br />{this.state.Actor.movieRatings[5]}</div></div></a>
                    </div></div>
                  </div></div>
                </div>
                <a class="carousel-control-prev" href="#myCarousel" data-slide="prev">
                </a>
                <a class="carousel-control-next" href="#myCarousel" data-slide="next">
                </a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4"><img class="actor-img" src={this.state.Actor.image} /></div>
      </div></div>
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
}