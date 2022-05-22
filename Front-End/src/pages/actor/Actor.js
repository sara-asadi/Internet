import React from "react";
import Header from "../general/Header";
import "./actor-styles.css"
import axios from 'axios';
import { Navigate } from "react-router-dom";

export default class Actor extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      queryParams: new URLSearchParams(window.location.search),
      actorId: new URLSearchParams(window.location.search).get("id"),
      actor: null,
      notFound: false,
      loading: false
    };
  }

  componentDidMount() {
    console.log("actor");

    document.title = "actor";
    if (this.state.actorId != null) {
      this.getActorById(this.state.actorId);
      console.log(this.state.actorId);
    }
  }

  async getActorById(id) {
    const getData = () => {
      const token = localStorage.getItem("token");
      console.log("token" + token);
      axios.get('http://localhost:8080/actors/' + id, {
        headers: {
          "Authorization": `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }).then(response => {
        console.log(response);
        let actor = response.data;
        if (actor == null) {
          this.setState({ notFound: true, });
          console.log("not found");
        }
        else {
          console.log(actor)
          this.setState({ actor: actor, });
          document.title = actor.name;
          console.log(actor.name);
        }
      }).catch(function (error) {
        console.log(error);
      });
    };
    getData();
  }

  render() {
    if (localStorage.getItem("token") === null) {
      return <Navigate to="/login" />
    }
    else {
      return (
        <div className="h-100">
          <Header />
          hi
          {this.state.actor !== null
            ? this.renderActor()
            : this.renderLoading()}
        </div>
      );
    }
  }

  renderActor() {
    console.log("render actor");

    return (
      <div><div className="row">
        <div className="col-sm-8">
          <div className="actor-desc-text text-center">مشخصات بازیگر</div>
          <div className="actor-desc-text text-right">نام: {this.state.actor.name}</div>
          <div className="actor-desc-text text-right">تاریخ تولد: {this.state.actor.birthDate}</div>
          <div className="actor-desc-text text-right">ملیت: {this.state.actor.nationality}</div>
          <div className="actor-desc-text text-right">تعداد فیلم ها: {this.state.actor.movieCount}</div>
          <div className="actor-desc-text text-center">فیلم ها</div>
          <div className="row">
            <div className="col-sm-1"></div>
            <div className="col-sm-10 actor-movies container">
              <div id="myCarousel" className="carousel slide" data-ride="carousel">
                <div className="carousel-inner">
                  <div className="carousel-item active">
                    <div className="row">
                    </div>
                  </div>
                </div>
                <a className="carousel-control-prev" href="#myCarousel" data-slide="prev">
                </a>
                <a className="carousel-control-next" href="#myCarousel" data-slide="next">
                </a>
              </div>
            </div>
          </div>
        </div>
        <div className="col-sm-4"><img className="actor-img" src={this.state.actor.image} /></div>
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