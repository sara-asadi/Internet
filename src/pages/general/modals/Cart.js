import * as React from "react";
import "../../../Assets/styles/cart-styles.css";
import UserService from "../../../services/UserService";
import {enToFaNumber} from "../../../utils/utils";
import {toast} from "react-toastify";
import CartItem from "./CartItem";
import {cartRefresh, creditRefresh} from "../../../services/subjects/MessageService";
import {OK, TOAST_MESSAGE_OK} from "../../../config/config";


export default class Cart extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            cart: {},
            isLoading: false,
            getCartLoading: false,
        };
        this._isMounted = false;
    }


    componentDidMount() {
        this._isMounted = true;
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
        this.getUserCart();
        cartRefresh.asObservable().subscribe(() => {
            this.getUserCart();
        })
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    async getUserCart() {
        this._isMounted && this.setState({getCartLoading: true});
        UserService.getCart().then(cart => {
            this._isMounted && this.setState({
                cart: cart.data,
                getCartLoading: false
            });
            console.log(this.state.cart);
        })
    }

    finalizeOrder() {
        this._isMounted && this.setState({isLoading: true});
        UserService.finalizeOrder().then(data => {
            if (data === OK) {
                toast.success(TOAST_MESSAGE_OK);
                cartRefresh.next();
                creditRefresh.next();
            } else {
                toast.error(data);
            }
            this._isMounted && this.setState({isLoading: false});
        }).catch(error => {
            console.log(error);
            toast.error(error.toString());
            this._isMounted && this.setState({isLoading: false});
        })
    }

    render() {
        return (
            <div className="cart-wrapper">
                <div className="cart-container">
                    {this.state.cart && this.state.cart.cartItems && (
                        <span className="cart-title">
                            سبد خرید
                        </span>
                    )}
                    {this.getCart()}
                </div>
            </div>
        );
    }

    renderGetCartLoading() {
        if (this.state.getCartLoading)
            return (
                <div className="spinner-grow text-danger food-party-loading-box align-self-center position-absolute"
                     role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            );

    }

    getCart() {
        if (this.state.cart && this.state.cart.cartItems)
            return (
                <>
                    <div
                        className={"cart-list-wrapper d-flex justify-content-center " + (this.state.getCartLoading ? "blur-loading" : "")}>
                        {(this.state.cart.cartItems.length === 0) &&
                        <div className="w-75">
                            <img src="https://www.digikala.com/static/files/68b7acd6.png" alt={""}/>
                            <div className="center-text mb-4">
                                سبد خرید شما خالی است!
                            </div>
                        </div>
                        }
                        {this.state.cart.cartItems.map((cartItem, i) => {
                            return (
                                <CartItem cartItem={cartItem} key={"CART" + i}/>
                            )
                        })}

                        {this.renderGetCartLoading()}
                    </div>
                    <div className="total-price">
                        جمع کل:
                        <span className={"p-1"}>
                            {enToFaNumber(this.state.cart.totalPrice || 0)}
                            <span className={"m-1"}/>
                            تومان
                        </span>
                    </div>

                    <button type="button" className="btn btn-default btn-cyan"
                            onClick={() => (!this.state.isLoading ? this.finalizeOrder() : {/*pass*/})}>
                        تایید نهایی
                        {this.state.isLoading &&
                        <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                        }
                    </button>

                </>
            );
        else
            return (
                <div className="spinner-grow text-danger food-party-loading-box align-self-center" role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            )
    }

}


