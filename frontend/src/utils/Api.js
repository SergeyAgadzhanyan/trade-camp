export default class Api {

  _checkResponse(res) {
    if (res.ok) {
      return res;
    }
    return Promise.reject('Something went wrong: ');
  }
}
