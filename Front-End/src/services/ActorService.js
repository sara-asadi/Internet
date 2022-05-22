import { ACTOR_URL } from "../config/config";

const axios = require("axios").default;
export default class ActorService {

  static async getActorById(id) {
    let actor = await axios.get(ACTOR_URL + id);
    if(actor.data !== "")
        return actor.data;
    else
        return null;
  }
}
