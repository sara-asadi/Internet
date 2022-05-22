import React from "react";
import PropTypes from "prop-types";

export default class CommentCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: false,
    };
  }

  render() {
    return (
      <div className="comment">
        <p>{this.props.Comment.userName}</p>
        <hr />
        <div>{this.props.Comment.text}</div>
        <div className="text-left">
          <div className="vote">
            <a href="#">
              <img src="like.png" />
            </a>
            <div className="caption">{this.props.Comment.likes}</div>
          </div>
          <div className="vote">
            <a href="#">
              <img src="dislike.png" />
            </a>
            <div className="caption">{this.props.Comment.disLikes}</div>
          </div>
        </div>
        <br />
      </div>
    );
  }
}

CommentCard.propTypes = {
  Comment: PropTypes.shape({
    userName: PropTypes.string,
    text: PropTypes.string,
    likes: PropTypes.number,
    disLikes: PropTypes.number
  }),
};
