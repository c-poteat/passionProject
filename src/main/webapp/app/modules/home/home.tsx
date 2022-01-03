import './home.scss';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import Axios from 'axios';
import './RecipeTile.css';
import './App.css';

function recipeSearch() {
  const [query, setquery] = useState(''); // use state is updating the value in the frontend
  const [recipes, setrecipes] = useState([]);

  const YOUR_APP_ID = '052a1f0c';
  const YOUR_APP_KEY = 'df4f0e14428f5599ab2a09346234d1d6';
  const url = `https://api.edamam.com/search?q=${query}&app_id=${YOUR_APP_ID}&app_key=${YOUR_APP_KEY}&from=0&to=12&calories=591-722`;

  //  `https://api.edamam.com/search?q=${query}&app_id=052a1f0c&app_key=df4f0e14428f5599ab2a09346234d1d6&health=alcohol-free`;

  async function getRecipes() {
    const result = await Axios.get(url);
    setrecipes(result.data.hits);
    console.log(result.data);
  }

  const onSubmit = e => {
    e.preventDefault(); // prevent page from reloading
    getRecipes();
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
            <Col md="5">
              <div className="app">
                <form className="app__searchForm" onSubmit={onSubmit}>
                  <input
                    type="text"
                    className="app__input"
                    placeholder="Find Recipes"
                    value={query}
                    onChange={e => setquery(e.target.value)}
                  />
                  <input className="app__submit" type="submit" value="Search" />
                </form>
              </div>
              <div className="app__recipes">
                {recipes.map(recipe => {
                  return (
                    <>
                      <div
                        className="recipeTile"
                        onClick={() => {
                          window.open(recipe['recipe']['url']);
                        }}
                      >
                        <img className="recipeTile__img" src={recipe['recipe']['image']} />
                        <p className="recipeTile__name">{recipe['recipe']['label']}</p>
                      </div>
                    </>
                  );
                })}
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
