import {enToFaNumber} from "../../../utils/utils";
import * as React from "react";
import UserService from "../../../services/UserService";
import "../../../Assets/styles/cart-styles.css";
import {toast} from "react-toastify";
import {cartRefresh} from "../../../services/subjects/MessageService";
import PropTypes from 'prop-types';


export default class CartItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false
        };

        this.incFood = this.incFood.bind(this);
        this.decFood = this.decFood.bind(this);
    }


    componentDidMount() {
        console.log(this.props);
    }

    incFood() {
        this.setState({isLoading: true});
        console.log(this.props);
        UserService.addToCart(this.props.cartItem.restaurant.id, this.props.cartItem.food.name).then(data => {
            toast.success('عملیات با موفقیت انجام شد');
            cartRefresh.next();
            this.setState({isLoading: false});
        }).catch(error => {
            toast.error(error.toString());
            this.setState({isLoading: false});
        })
    }


    decFood() {
        this.setState({isLoading: true});
        UserService.removeFromCart(this.props.cartItem.restaurant.id, this.props.cartItem.food.name).then(data => {
            toast.success('عملیات با موفقیت انجام شد');
            cartRefresh.next();
            this.setState({isLoading: false});
        }).catch(error => {
            toast.error(error.toString());
            this.setState({isLoading: false});
        })

    }


    render() {
        return (
            <>
                <div className={"cart-list-item-wrapper d-flex flex-column " + (this.state.isLoading ? "blur-loading" : "")}>
                    <div className="d-flex flex-row justify-space-between">
                        <div className="align-self-start">
                            {this.props.cartItem.food.name}
                        </div>
                        <div className="align-self-end mr-auto d-flex align-items-center">
                            <div onClick={this.decFood}><i className="glyph-icon flaticon-minus cart-icon clickable-tag"/></div>
                            <span className="p-1">
                                {enToFaNumber(this.props.cartItem.quantity)}
                            </span>
                            <div onClick={this.incFood}><i className="glyph-icon flaticon-plus cart-icon clickable-tag"/></div>
                        </div>
                    </div>
                    <div className="price d-flex flex-row justify-content-end">
                        {enToFaNumber(this.props.cartItem.food.price * this.props.cartItem.quantity)}
                        تومان
                    </div>
                </div>
                <div className="break"/>
            </>
        );
    }
}

CartItem.propTypes = {
    cartItem: PropTypes.shape({
        food: PropTypes.shape({
            name: PropTypes.string,
            price: PropTypes.number
        }),
        restaurant: PropTypes.shape({
            id: PropTypes.string
        }),
        quantity: PropTypes.number
    })
};