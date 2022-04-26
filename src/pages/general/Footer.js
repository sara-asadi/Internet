import React from "react";
import "../../Assets/styles/footer.css";

export default class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            test: 1
        };
    }

    render() {
        return (
            <footer>
                <div className="row no-gutters justify-content-center align-items-center footer-inner-box">
                    <div className="col-auto">
                        <div className="copy-right-icon">
                    <span>
                        ©
                    </span>
                        </div>
                    </div>
                    <div className="col-auto">
                        <div className="copy-right-text">
                    <span>
                        تمامی حقوق متعلق به لقمه است.
                    </span>
                        </div>
                    </div>
                </div>
            </footer>
        );
    }
}