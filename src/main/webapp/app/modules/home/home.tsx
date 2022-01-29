import './home.scss';
import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import Axios from 'axios';
import './RecipeTile.css';
import './App.css';

function recipeSearch() {
  const [query, setquery] = useState(''); // use state is updating the value in the frontend
  const [recipes, setrecipes] = useState([]);

  const url = `https://api.edamam.com/search?q=${query}&app_id=052a1f0c&app_key=df4f0e14428f5599ab2a09346234d1d6`;

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
          <div>
            <Col md="5">
              <div className="app">
                <form className="app__searchForm" onSubmit={onSubmit}>
                  <input
                    type="text"
                    className="app__input-max"
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
                      <div>
                        <div className="card">
                          <div className="card-name"> {recipe['recipe']['label']}</div>
                          <img className="recipeTile__img" src={recipe['recipe']['image']} />
                          <button className='view-recipe'
                          onClick={() => {window.open(recipe['recipe']['url']);}}>View Recipe</button>
                        </div>
                      </div>
                    </>
                  );
                })}
              </div>
            </Col>
            <Alert color="light"></Alert>
          </div>
          <div>
          </div>
      </Col>
    </Row>
  );
}

export default recipeSearch;
