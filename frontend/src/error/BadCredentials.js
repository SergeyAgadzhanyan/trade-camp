export default class BadCredentials extends Error {

  constructor(message) {
    super(message);
    this.name = 'BadCredentials';
  }
}
