import React from "react";
import PropTypes from "prop-types";

export default class ActorCard extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          redirect: false,
        };
    debugger
  }

  render() {
    return (
      <div className="col-sm-3">
        <div className="container-2">
          <a href="#">
            <img src={this.props.Actor.image} className="image-2" />
            <div className="overlay-2">
              <div className="text-2">
                {this.props.Actor.name}
                <br />
                {this.props.Actor.age}
              </div>
            </div>
          </a>
        </div>
      </div>
    );
  }
}

ActorCard.propTypes = {
  Actor: PropTypes.shape({
    age: PropTypes.string,
    name: PropTypes.string,
    image: PropTypes.string,
  }),
};
