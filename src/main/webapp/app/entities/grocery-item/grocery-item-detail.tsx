import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './grocery-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GroceryItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const groceryItemEntity = useAppSelector(state => state.groceryItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groceryItemDetailsHeading">
          <Translate contentKey="recipeApplicationApp.groceryItem.detail.title">GroceryItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{groceryItemEntity.id}</dd>
          <dt>
            <span id="item">
              <Translate contentKey="recipeApplicationApp.groceryItem.item">Item</Translate>
            </span>
          </dt>
          <dd>{groceryItemEntity.item}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="recipeApplicationApp.groceryItem.category">Category</Translate>
            </span>
          </dt>
          <dd>{groceryItemEntity.category}</dd>
        </dl>
        <Button tag={Link} to="/grocery-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">{/* <Translate contentKey="entity.action.back">Back</Translate> */}</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grocery-item/${groceryItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">{/* <Translate contentKey="entity.action.edit">Edit</Translate> */}</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroceryItemDetail;
