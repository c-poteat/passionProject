import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FavoriteRecipes from './favorite-recipes';
import FavoriteRecipesDetail from './favorite-recipes-detail';
import FavoriteRecipesUpdate from './favorite-recipes-update';
import FavoriteRecipesDeleteDialog from './favorite-recipes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FavoriteRecipesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FavoriteRecipesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FavoriteRecipesDetail} />
      <ErrorBoundaryRoute path={match.url} component={FavoriteRecipes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FavoriteRecipesDeleteDialog} />
  </>
);

export default Routes;
