import React from "react";
import "../../Assets/styles/header.css";
import "bootstrap/dist/css/bootstrap.css";
import Dropdown from "react-bootstrap/Dropdown";

export default class Header extends React.Component {
  constructor(props) {
    super(props);
    this.handleShow = this.handleShow.bind(this);
    this.handleClose = this.handleClose.bind(this);

    this.state = {
      show: true,
    };
  }

  handleClose() {
    this.setState({ show: false });
  }

  handleShow() {
    this.setState({ show: true });
  }

  componentDidMount() {}

  render() {
    return (
      <nav className="navbar default-navbar">
        <div className="container-fluid">
          <div className="navbar-header navbar-left col-md-3">
            <a href="#">
              {/* <img src="./../../Assets/images/template.png" className="logo" /> */}
            </a>
          </div>
          <div className="nav navbar-form col-md-2">
            <Dropdown>
              <Dropdown.Toggle variant="Secondary" className="btn dropdown-toggle">
                :جستجو بر اساس
                <span className="caret"></span>
              </Dropdown.Toggle>
              <Dropdown.Menu className="dropdown-menu">
                <Dropdown.Item href="#">نام</Dropdown.Item>
                <Dropdown.Item href="#">ژانر</Dropdown.Item>
                <Dropdown.Item href="#">تاریخ تولید</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </div>
          {/* <form action="movies" method="POST" className="navbar-form col-md-3">
            <div className="input-group">
              <input
                type="text"
                className="form-control"
                placeholder="Search"
                name="search"
              />
              <div className="input-group-btn">
                <button
                  className="btn btn-search"
                  type="submit"
                  name="action"
                  value="search"
                >
                  <i className="glyphicon glyphicon-search"></i>
                </button>
              </div>
            </div>
          </form> */}
          {/* <div className="nav navbar-nav navbar-right col-md-1 user">
            <li>
              <a href="#">
                <span
                  className="iconify-inline"
                  data-icon="carbon:user-avatar-filled"
                ></span>
              </a>
            </li>
          </div> */}
        </div>
      </nav>
    );
  }
}
