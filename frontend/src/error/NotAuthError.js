export default class NotAuthError extends Error {

  constructor(message, url) {
    super(message);
    this.name = 'NotAuthError';
    this.redirectedUrl = url;
  }
}
