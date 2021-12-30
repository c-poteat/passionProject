import './home.scss';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import styled from 'styled-components';

const Button = styled.button`
  background-color: black;
  display: inline-block;
  color: white;
  }
`;

function recipeSearch() {
  const [recipe, setRecipe] = useState('');
  const handleClick = e => {
    fetch('https://api.chucknorris.io/jokes/random')
      // Placeholder API until errors get fixed
      .then(response => response.json())
      .then(data => {
        setRecipe(data.value + '...' + data.month);
        // eslint-disable-next-line no-console
      });
  };

  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="5">
        <h2>
          <Translate contentKey="home.title">RecipeLink</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">Main Menu</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
            <Col md="5">
              <h6>
                <input type="text" placeholder="search for recipes..."></input>
              </h6>

              <div>
                <Alert color="light"></Alert>
                <button onClick={handleClick}>Get Recipes</button>
                <Alert color="light"></Alert>
                {recipe}
              </div>
            </Col>
            <Alert color="light"></Alert>
          </div>
        ) : (
          <div>
            <Alert color="success">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>
            <Alert color="success">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
            <Alert color="light"></Alert>
          </div>
        )}
      </Col>
    </Row>
  );
}

export default recipeSearch;
