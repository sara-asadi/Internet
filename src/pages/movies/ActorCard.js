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

  getAge(date){
    var today = new Date();
    var birthDate = new Date(date);
    var age = today. getFullYear() - birthDate. getFullYear();
    return age;
  }

  render() {
    return (
      <div className="col-sm-3">
        <div className="container-2">
          <a href="#">
            <img src={this.props.Actor.image} className="image-2" />
            <div className="overlay-2">
              <div className="text-1">
                {this.props.Actor.name}
                <br />
                {/* {this.getAge(this.props.Actor.birthDate)} */}
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
    birthDate: PropTypes.string,
    name: PropTypes.string,
    image: PropTypes.string,
  }),
};
