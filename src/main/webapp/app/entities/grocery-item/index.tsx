import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GroceryItem from './grocery-item';
import GroceryItemDetail from './grocery-item-detail';
import GroceryItemUpdate from './grocery-item-update';
import GroceryItemDeleteDialog from './grocery-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GroceryItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GroceryItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GroceryItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={GroceryItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GroceryItemDeleteDialog} />
  </>
);

export default Routes;
