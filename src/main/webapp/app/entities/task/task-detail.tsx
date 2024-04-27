import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './task.reducer';

export const TaskDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">
          <Translate contentKey="taskManagerApp.task.detail.title">Task</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="taskManagerApp.task.title">Title</Translate>
            </span>
          </dt>
          <dd>{taskEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="taskManagerApp.task.description">Description</Translate>
            </span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="executionTime">
              <Translate contentKey="taskManagerApp.task.executionTime">Execution Time</Translate>
            </span>
          </dt>
          <dd>{taskEntity.executionTime ? <TextFormat value={taskEntity.executionTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="durationMin">
              <Translate contentKey="taskManagerApp.task.durationMin">Duration Min</Translate>
            </span>
          </dt>
          <dd>{taskEntity.durationMin}</dd>
          <dt>
            <span id="closed">
              <Translate contentKey="taskManagerApp.task.closed">Closed</Translate>
            </span>
          </dt>
          <dd>{taskEntity.closed ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="taskManagerApp.task.user">User</Translate>
          </dt>
          <dd>{taskEntity.user ? taskEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="taskManagerApp.task.tag">Tag</Translate>
          </dt>
          <dd>
            {taskEntity.tags
              ? taskEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {taskEntity.tags && i === taskEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
