import { MOVIES_URL } from "../config/config";

const axios = require("axios").default;
export default class MovieService {

  static async getMovies() {
    let movies = await axios.get("");
    return movies.data
  }

  static async getMovieById(id) {
    debugger
    let movie = await axios.get(MOVIES_URL+id);
    if(movie.data !== "")
        return movie.data
    else return null
  }

}
