import * as React from "react";
import "../../../Assets/styles/food-modal-styles.scss";
import {enToFaNumber} from "../../../utils/utils";
import UserService from "../../../services/UserService";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {OK, TOAST_MESSAGE_OK} from "../../../config/config";
import {cartRefresh} from "../../../services/subjects/MessageService";
import PropTypes from "prop-types";


export default class Food extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userChosenEntity: 1,
            isLoading: false
        };
        this._isMounted = false;
    }

    componentDidMount() {
        this._isMounted = true;
        toast.configure({rtl: true, className: "text-center"});
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    async addToCart() {
        this._isMounted && this.setState({isLoading: true});
        let response = await UserService.addToCart(this.props.food.restaurantId, this.props.food.name, this.state.userChosenEntity);
        if (response === OK) {
            toast.success(TOAST_MESSAGE_OK, {
                position: "top-right"
            });
            cartRefresh.next();
        } else {
            toast.error(response.toString(), {
                position: "top-right",
            });
        }
        this._isMounted && this.setState({isLoading: false});
    }

    incrementEntity() {
        console.log(this.props.food.count);
        if (this.state.userChosenEntity < this.props.food.count || this.props.food.oldPrice === undefined) {
            this.setState({
                userChosenEntity: this.state.userChosenEntity + 1
            })
        }
    }

    decrementEntity() {
        if (this.state.userChosenEntity > 1) {
            this.setState({
                userChosenEntity: this.state.userChosenEntity - 1
            })
        }
    }

    render() {
        return (
            <div>
                <div className="top-part">
                    <div className="restaurant-name">
                        {this.props.food.restaurantName}
                    </div>
                    <div className="food-wrapper">
                        <div className="food-img-wrapper">
                            <div className="food-img">
                                <img src={this.props.food.image} alt=""/>
                            </div>
                        </div>
                        <div className="food-detail-wrapper">
                            <div className="food-detail">
                                <div className="name-rate">
                                    <div className="food-name">
                                        {this.props.food.name}
                                    </div>
                                    <div className="food-rate">
                                        {enToFaNumber(this.props.food.popularity * 5)}
                                        <span className="star-icon">⋆</span>
                                    </div>
                                </div>
                                <div className="food-description">
                                    {this.props.food.description}
                                </div>
                                <div className="food-prices">
                                    {(this.props.food.oldPrice !== undefined) &&
                                    <div className="old-price">
                                        {enToFaNumber(this.props.food.oldPrice)}
                                    </div>
                                    }
                                    <div className="curr-price">
                                        {enToFaNumber(this.props.food.price)} تومان
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div className="separator-border"/>


                <div className="bottom-part">

                    <div className={"remaining " + (this.props.food.count !== undefined ? "visible" : "invisible")}>
                        <span> موجودی : </span>
                        {
                            this.props.food.count == null ? (<span> - </span>) : (
                                <span> {enToFaNumber(this.props.food.count)} </span>)
                        }

                    </div>

                    <div className="actions">
                        <div className="inc-amount mx-1" onClick={() => this.incrementEntity()}>
                            <i className="flaticon-plus"/>
                        </div>
                        <div className="amount mx-1">
                            {enToFaNumber(this.state.userChosenEntity)}
                        </div>
                        <div className="dec-amount mx-1" onClick={() => this.decrementEntity()}>
                            <i className="flaticon-minus"/>
                        </div>
                        <div className="add-to-cart mx-2"
                             onClick={() => (!this.state.isLoading ? this.addToCart() : {/*pass*/})}>
                            <span>
                                افزودن به سبد خرید
                            </span>
                            {this.state.isLoading &&
                            <span className="spinner-border spinner-border-sm m-1" role="status" aria-hidden="true"/>
                            }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Food.propTypes = {
    food: PropTypes.shape({
        restaurantId: PropTypes.string,
        restaurantName: PropTypes.string,
        name: PropTypes.string,
        description: PropTypes.string,
        price: PropTypes.number,
        oldPrice: PropTypes.number,
        count: PropTypes.number,
        popularity: PropTypes.number,
        image: PropTypes.string
    })
};