import { MOVIES_URL, MOVIESCAST_URL, MOVIESCOMMENTS_URL } from "../config/config";

const axios = require("axios").default;
export default class MovieService {

  static async getMovies() {
    debugger
    const response = await fetch(MOVIES_URL);
    const json = await response.json();
    return json;
  }

  static async getMovieById(id) {
    debugger
    let movie = await axios.get(MOVIES_URL+id);
    if(movie.data !== "")
        return movie.data
    else return null
  }

  static async getActorsById(id) {
    debugger
    let actors = await axios.get(MOVIESCAST_URL+id);
    if(actors.data !== "")
        return actors.data
    else return null
  }

  static async getActorsById(id) {
    debugger
    let actors = await axios.get(MOVIESCAST_URL+id);
    if(actors.data !== "")
        return actors.data
    else return null
  }

  static async getCommentsById(id) {
    debugger
    let comments = await axios.get(MOVIESCOMMENTS_URL+id);
    if(comments.data !== "")
        return comments.data
    else return null
  }



}
