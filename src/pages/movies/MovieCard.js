import React from "react";
import PropTypes from "prop-types";

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
        <span className="container-1">
          <a href="#">
            <img src={this.props.Movie.image} className="poster image-1" />
            <div className="overlay-1">
              <div id="movieCard" className="text-1">
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
