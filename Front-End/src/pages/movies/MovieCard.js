import React from "react";
import PropTypes from "prop-types";
import "./Movies.css"
import { Link } from "react-router-dom";
export default class MovieCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: false,
    };
  }

  render() {
    return (
      <div className="row">
        //<div className="col-sm-11">
          <span class="container-1">
            <Link to={"/movies?id=" + this.props.Movie.id}>
              <img src={this.props.Movie.image} className="" />
              <div class="overlay-1">
                <div class="text-1">
                  {this.props.Movie.name}
                  <br />
                  {this.props.Movie.imdbRate}
                </div>
              </div>
            </Link>
          </span>
        </div>
      </div>
    );
  }
}

MovieCard.propTypes = {
  Movie: PropTypes.shape({
    image: PropTypes.string,
    name: PropTypes.string,
    imdb: PropTypes.number,
  }),
};
