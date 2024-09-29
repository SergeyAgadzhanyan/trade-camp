export default class NotFoundError extends Error {

  constructor(message, url) {
    super(message);
    this.name = 'NotFoundError';
    this.redirectedUrl = url;
  }
}
