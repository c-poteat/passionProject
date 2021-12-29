import React, { useState, useEffect } from 'react';
import { Redirect, RouteComponentProps } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import LoginModal from './login-modal';

export const Login = (props: RouteComponentProps<any>) => {
  const dispatch = useAppDispatch();
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const loginError = useAppSelector(state => state.authentication.loginError);
  const showModalLogin = useAppSelector(state => state.authentication.showModalLogin);
  const [showModal, setShowModal] = useState(showModalLogin);

  useEffect(() => {
    setShowModal(true);
  }, []);

  const handleLogin = (username, password, rememberMe = false) => dispatch(login(username, password, rememberMe));
  <a href="https://spoonacular.com/">Search for recipes on spoonacular!</a>;
  const handleClose = () => {
    setShowModal(false);
    props.history.push('/');
  };

  const { location } = props;
  const { from } = (location.state as any) || { from: { pathname: '/', search: location.search } };
  if (isAuthenticated) {
    return <Redirect to={from} />;
  }
  return <LoginModal showModal={showModal} handleLogin={handleLogin} handleClose={handleClose} loginError={loginError} />;
  <a href="https://spoonacular.com/">Search for recipes on spoonacular!</a>;
};

export default Login;
