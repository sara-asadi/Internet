import React from "react";
import PropTypes from "prop-types";
import "./Movies.css"
export default class MovieCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: false,
    };
    debugger;
  }

  render() {
    return (
      <span class="container-1">
        <a href="#">
          <img src={this.props.Movie.image} className="poster image-1"  />
          <div class="overlay-1">
            <div class="text-1">
              {this.props.Movie.name}
              <br />
              {this.props.Movie.imdbRate}
            </div>
          </div>
        </a>
      </span>
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