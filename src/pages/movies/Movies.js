import React from "react";
import MovieService from "../../services/MovieService";

export default class Movies extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            queryParams: new URLSearchParams(window.location.search),
            MovieId: new URLSearchParams(window.location.search).get("id"),
            Movie: null,
            notFound: false
        }
    }

    componentDidMount() {
        document.title = "Movies";
        // scrollToTop();
        if (this.state.MovieId == null) {
            // this.props.history.push("/");
        } else {
            this.getMovieById(this.state.MovieId);
        }
    }

    async getMovieById(id) {
        debugger
        let Movie = await MovieService.getMovieById(id);
        if (Movie == null) {
            this.setState({
                notFound: true
            });
        } else {
            this.setState({
                Movie: Movie
            });
            document.title = Movie.name;
        }
    }

    render() {
        return (
            <div className="h-100">
                hi
                {/* <Header/>
                {this.state.Movie !== null ?
                    // this.renderMain()
                    :
                    // this.renderLoading()
                }
                <Footer/> */}
            </div>
        );
    }

    // renderLoading() {
    //     return (
    //         <div className="h-100 m-5 center-text p-5">
    //             <div className="spinner-grow text-danger m-5" role="status">
    //                 <span className="sr-only">Loading...</span>
    //             </div>
    //         </div>
    //     );
    // }

    // renderMain() {
    //     return (
    //         <main>
    //             <div className="sub-header-red">
    //                 <div className="sub-header-over">
    //                     <img alt="" className="sub-header-over-img"
    //                          src={this.state.Movie.logo}/>
    //                 </div>
    //             </div>
    //             <div className="sub-header-over-text">{this.state.Movie.name}</div>

    //             <div className="container restaurant-main-container">

    //                 <div className="row">
    //                     <div className="col-lg-4 col-6 center-text"/>
    //                     <div className="col-lg-8 col-6 center-text">
    //                 <span className="menu-text">
    //                     منوی غذا
    //                 </span>
    //                     </div>
    //                 </div>
    //                 <div className="row mt-5">

    //                     <div className="col-lg-4 col-6">
    //                         <Cart/>
    //                     </div>

    //                     <div className="col-lg-8 col-6 food-list-container">
    //                         <div className="row">


    //                             {/* {this.renderMenu()} */}

    //                         </div>
    //                     </div>
    //                 </div>
    //             </div>

    //         </main>
    //     );
    // }
}
