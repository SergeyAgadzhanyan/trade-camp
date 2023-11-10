import React from 'react';

function Login() {

  function handleSubmit(e) {
    e.preventDefault();
    const params = new URLSearchParams({
      'user': 'u1',
      'pass': 'p1',
    });

    fetch('http://localhost:8080/perform_login', {
      method: 'POST',
      body: params,
      credentials: 'include',
    })
        .then(() => window.location = "/")
        .catch(e => console.log(e));
  }

  function handleClick() {
    document.location.assign('http://localhost:3000/');
  }

  function handleGetMe() {
    fetch('http://localhost:8080/user/me', {
      method: 'Get',
      credentials: 'include',
    })
        .then((r) => r.json())
        .then(user => console.log(user.name))
        .catch(e => console.log(e));
  }

  return (
      <div className="App">
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="exampleFormControlInput1" className="form-label">Email
              address</label>
            <input className="form-control"
                   id="user" name="user" placeholder="user"/>
          </div>
          <div className="mb-3">
            <input type="password" className="form-control"
                   id="pass" name="pass" placeholder="password"/>
          </div>
          <input type="submit" value="Login"/>
        </form>
        <button onClick={handleClick}>go</button>
        <button onClick={handleGetMe}>get me</button>
      </div>
  );
}

export default Login;
