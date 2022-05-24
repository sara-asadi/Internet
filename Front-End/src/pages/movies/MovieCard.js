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
          <span className="container-1">
            <Link to={"/movies?id=" + this.props.movie.id}>
              <img src={this.props.movie.image} className="" />
              <div className="overlay-1">
                <div className="text-1">
                  {this.props.movie.name}
                  <br />
                  {this.props.movie.imdbRate}
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
  movie: PropTypes.shape({
    image: PropTypes.string,
    name: PropTypes.string,
    imdb: PropTypes.number,
  }),
};
