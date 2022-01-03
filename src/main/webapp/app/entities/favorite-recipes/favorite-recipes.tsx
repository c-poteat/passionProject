import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './favorite-recipes.reducer';
import { IFavoriteRecipes } from 'app/shared/model/favorite-recipes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FavoriteRecipes = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const favoriteRecipesList = useAppSelector(state => state.favoriteRecipes.entities);
  const loading = useAppSelector(state => state.favoriteRecipes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="favorite-recipes-heading" data-cy="FavoriteRecipesHeading">
        <Translate contentKey="recipeApplicationApp.favoriteRecipes.home.title">Favorite Recipes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" size="sm" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="recipeApplicationApp.favoriteRecipes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="recipeApplicationApp.favoriteRecipes.home.createLabel">Create new Favorite Recipes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {favoriteRecipesList && favoriteRecipesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="recipeApplicationApp.favoriteRecipes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="recipeApplicationApp.favoriteRecipes.favoritelinks">Favoritelinks****</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {favoriteRecipesList.map((favoriteRecipes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${favoriteRecipes.id}`} color="primary" size="sm">
                      {favoriteRecipes.id}
                    </Button>
                  </td>
                  <td>
                    <a href={favoriteRecipes.favoritelinks}>
                      <div>{favoriteRecipes.favoritelinks}</div>
                    </a>
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${favoriteRecipes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${favoriteRecipes.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${favoriteRecipes.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-info">
              <Translate contentKey="recipeApplicationApp.favoriteRecipes.home.notFound">No Favorite Recipes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FavoriteRecipes;
