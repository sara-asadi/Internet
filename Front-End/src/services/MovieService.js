const axios = require("axios").default;
export default class MovieService {

  /* 
  static async getMovies() {
    axios.get('http://localhost:8080/movies', {
      headers: {
        "Authorization": `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(response => {
      console.log("response:");
      console.log(response);
      this.setState({ actor: response.data });
      document.title = response.data.name;
    }).catch(function (error) {
      if (error) {
        console.log(error);
        console.log(error.data);
        toast.error("error 404: Not Found!");
      }
    });
    const response = await fetch(MOVIES_URL);
    const json = await response.json();
    return json;
  }
 static async getMovieById(id) {
      let movie = await axios.get(MOVIES_URL + id);
      if (movie.data !== "")
        return movie.data
      else return null
    }
  */
  static async AddToWatchlist(id) {
    await axios.get('http://localhost:8080/movies/add/' + id);
  }

  static async getActorsById(id) {

    let actors = await axios.get('http://localhost:8080/movies/cast/' + id);
    if (actors.data !== "")
      return actors.data
    else return null
  }

  static async getCommentsById(id) {

    let comments = await axios.get('http://localhost:8080/movies/comments/' + id);
    if (comments.data !== "")
      return comments.data
    else return null
  }

}
