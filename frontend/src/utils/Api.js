import NotAuthError from '../error/NotAuthError';
import NotFoundError from '../error/NotFoundError';

export default class Api {

  _checkResponse(res) {
    if (res.ok) {
      return res;
    }

    res.text().then(data => {
      console.log(data);
    });
    if (res.status === 401) {
      throw new NotAuthError('fail login', res.url);
    }
    if (res.status === 404) {
      throw new NotFoundError('not found', res.url);
    }
    return Promise.reject('Something went wrong: ');
  }
}
