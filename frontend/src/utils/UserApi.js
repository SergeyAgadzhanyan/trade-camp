import Api from "./Api.js";
import {localhostUrl, serverMod, serverUrl} from "./ServerConfig";

class UserApi extends Api {
    constructor({url, auth}) {
        super({url, auth});
    }

    getUserInfo() {
        return fetch(this._url, {
            headers: {
                authorization: this._auth,
            },
            credentials: 'include'
        }).then(this._checkResponse);
    }

    updateInfo(name, feature) {
        return fetch(this._url, {
            method: "PATCH",
            headers: {
                authorization: this._auth,
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({
                name: name,
                about: feature,
            }),
        }).then(this._checkResponse);
    }

    updateAvatar(link) {
        return fetch(this._url + "/avatar", {
            method: "PATCH",
            headers: {
                authorization: this._auth,
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({
                avatar: link,
            }),
        }).then(this._checkResponse);
    }
}

export default new UserApi({
    url: serverMod === 'dev' ? `${localhostUrl}/users/me` : `${serverUrl}/users/me`,
    auth: "83b38506-64f5-462f-9bf3-410e2163a0f8",
});
