import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './grocery-item.reducer';
import { IGroceryItem } from 'app/shared/model/grocery-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GroceryItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const groceryItemList = useAppSelector(state => state.groceryItem.entities);
  const loading = useAppSelector(state => state.groceryItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="grocery-item-heading" data-cy="GroceryItemHeading">
        <Translate contentKey="recipeApplicationApp.groceryItem.home.title">Grocery Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="recipeApplicationApp.groceryItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="recipeApplicationApp.groceryItem.home.createLabel">Create new Grocery Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {groceryItemList && groceryItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="recipeApplicationApp.groceryItem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="recipeApplicationApp.groceryItem.item">Item</Translate>
                </th>
                <th>
                  <Translate contentKey="recipeApplicationApp.groceryItem.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {groceryItemList.map((groceryItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${groceryItem.id}`} color="link" size="sm">
                      {groceryItem.id}
                    </Button>
                  </td>
                  <td>{groceryItem.item}</td>
                  <td>
                    <Translate contentKey={`recipeApplicationApp.Category.${groceryItem.category}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${groceryItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${groceryItem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${groceryItem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
            <div className="alert alert-warning">
              <Translate contentKey="recipeApplicationApp.groceryItem.home.notFound">No Grocery Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default GroceryItem;
