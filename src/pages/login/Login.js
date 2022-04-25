import "../../Assets/styles/login-styles.css";
import * as React from "react";
import { Link } from "react-router-dom";

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    document.title = "Sign in";
  }

  render() {
    return (
      <div class="container-login" dir="ltr">
        <div class="container p-1">
          <h2>Login</h2>
          <form action="" method="POST">
            <div class="form-group">
              <label for="email">Email:</label>
              <input
                type="email"
                class="form-control"
                id="email"
                placeholder="Enter email"
                name="email"
              />
            </div>
            <div class="form-group">
              <label for="pwd">Password:</label>
              <input
                type="password"
                class="form-control"
                id="pwd"
                placeholder="Enter password"
                name="password"
              />
            </div>
            <button type="submit" class="btn btn-success">
              Login
            </button>
          </form>
        </div>
      </div>

      // <div className="main-box w-100 h-100">
      //     <div className="d-flex flex-row-reverse no-gutters h-100">
      //         <div className="col-lg-9 col-md-8 h-100">
      //             <img className="background-img" src={require("../../Assets/images/food.jpg")} alt="" />
      //         </div>
      //         <div className="col-lg-3 col-md-4 overflow-auto">
      //             <div className="form-container">
      //                 <div className="d-flex">
      //                     <h1>
      //                         ورود
      //                     </h1>
      //                 </div>
      //                 <div className="d-flex justify-content-center align-items-center ">
      //                     <form action="tmp" className=" w-100">
      //                         <div className="">
      //                             <div className="form-item">
      //                                 <div className="d-flex">
      //                                     <label htmlFor="mail">
      //                                         ایمیل
      //                                     </label>
      //                                 </div>
      //                                 <input type="text" name="mail" id="mail" placeholder="ایمیل" />
      //                             </div>
      //                             <div className="form-item">
      //                                 <div className="d-flex">
      //                                     <label htmlFor="password">
      //                                         رمز عبور
      //                                     </label>
      //                                 </div>
      //                                 <input type="password" name="password" id="password"
      //                                        placeholder="***********" />
      //                             </div>

      //                             <div className="form-submit row">
      //                                 <div className="col-auto">
      //                                     <input type="submit" value="ورود" />
      //                                 </div>
      //                                 <div
      //                                     className="col-auto d-flex justify-content-center align-items-center clickable">
      //                                     <div className="login-arrow">
      //                                         <i className="flaticon-arrow"></i>
      //                                     </div>
      //                                     <span>
      //                                         <Link to="/signup">
      //                                             ثبت‌نام
      //                                         </Link>
      //                                     </span>
      //                                 </div>
      //                             </div>
      //                         </div>

      //                     </form>
      //                 </div>
      //             </div>
      //         </div>
      //     </div>
      // </div>
    );
  }
}
