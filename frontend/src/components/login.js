import React from 'react';
import Text from '../utils/text';
import AuthApi from '../utils/AuthApi';

function Login() {
  const [name, setName] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [submitName, setSubmitName] = React.useState(Text.SIGNIN);
  const [linkName, setLinkName] = React.useState(Text.CreateNewAcc);
  const [submitLink, setSubmitLink] = React.useState(Text.LinkSignIn);

  function handleSubmit(e) {
    e.preventDefault();
    if (submitName === Text.SIGNIN) return AuthApi.signIn({name, password})
        .then(() => window.location = '/')
        .catch(e => console.log(e));
    return AuthApi.signUp({name, password})
        .then((r) => r.json())
        .then(d => console.log(d))
        .catch(e => console.log(e));
  }

  // function handleClick() {
  //   document.location.assign('http://localhost:3000/');
  // }
  //
  // function handleGetMe() {
  //   fetch('http://localhost:8080/user/me', {
  //     method: 'Get',
  //     credentials: 'include',
  //   })
  //       .then((r) => r.json())
  //       .then(user => console.log(user.name))
  //       .catch(e => console.log(e));
  // }

  function handleNameChange(e) {
    setName(e.target.value);
  }

  function handlePasswordChange(e) {
    setPassword(e.target.value);
  }

  function handleLinkClick() {
    if (submitName === Text.SIGNIN) {
      setSubmitName(Text.SIGNUP);
      setLinkName(Text.AlreadyHave);
      setSubmitLink(Text.LinkSignUp);
    } else {
      setSubmitName(Text.SIGNIN);
      setLinkName(Text.CreateNewAcc);
      setSubmitLink(Text.LinkSignIn);
    }
    setName('');
    setPassword('');
  }

  return (
      <div className="login">
        <form className="login__form" onSubmit={handleSubmit}>
          <h1 className="login__title">Welcome</h1>
          <input className="login__input" placeholder="Name" value={name}
                 onChange={handleNameChange}/>
          <input className="login__input" value={password}
                 onChange={handlePasswordChange}
                 placeholder="Password"/>
          <button className={`login__button animate__animated ${submitName ===
          Text.SIGNIN
              ? 'animate__backInLeft'
              : 'animate__backInRight'}
              `} type="submit">{submitName}</button>
          <button type={'button'}
                  className={`login__button-link animate__animated ${submitName ===
                  Text.SIGNIN
                      ? 'animate__backInRight'
                      : 'animate__backInLeft'} `}
                  onClick={handleLinkClick}>{linkName}</button>

        </form>
      </div>
  );
}

export default Login;
