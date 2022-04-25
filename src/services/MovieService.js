const axios = require("axios").default;
export default class MovieService {

  static async getMovies() {
    let movies = await axios.get("");
    return movies.data
  }

  static async getMovieById(id) {
    let movie = await axios.get(""+id);
    if(movie.data !== "")
        return movie.data
    else return null
  }

}
