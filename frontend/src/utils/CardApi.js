import Api from "./Api.js";
import {localhostUrl, serverMod, serverUrl} from "./ServerConfig";

class CardApi extends Api {
    constructor({url, auth}) {
        super({url, auth});
    }

    getCards() {
        return fetch(this._url, {
            headers: {
                authorization: this._auth,
            },
            credentials: 'include'
        }).then(this._checkResponse);
    }

    addCard({name, link}) {
        return fetch(this._url, {
            method: "POST",
            headers: {
                authorization: this._auth,
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({
                name: name,
                link: link,
            }),
        }).then(this._checkResponse);
    }

    deleteCard(id) {
        return fetch(this._url + "/" + id, {
            method: "DELETE",
            headers: {
                authorization: this._auth,
            },
            credentials: "include"
        }).then(this._checkResponse);
    }

    like(id, isLiked) {
        const method = isLiked ? "DELETE" : "PUT";
        return fetch(this._url + "/" + id + "/likes", {
            method: method,
            headers: {
                authorization: this._auth,
            },
            credentials: "include"
        }).then(this._checkResponse);
    }
}

const cardApi = new CardApi({
    url: serverMod === 'dev' ? `${localhostUrl}/cards` : `${serverUrl}/cards`,
    auth: "83b38506-64f5-462f-9bf3-410e2163a0f8",
});
export default cardApi;
