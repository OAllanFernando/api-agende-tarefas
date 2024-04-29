package com.task.manager.service;

import com.task.manager.domain.Task;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.task.manager.domain.Task}.
 */
public interface TaskService {
    /**
     * Save a task.
     *
     * @param task the entity to save.
     * @return the persisted entity.
     */
    Task save(Task task);

    /**
     * Updates a task.
     *
     * @param task the entity to update.
     * @return the persisted entity.
     */
    Task update(Task task);

    /**
     * Partially updates a task.
     *
     * @param task the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Task> partialUpdate(Task task);

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Task> findAll(Pageable pageable);

    /**
     * Get all the tasks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Task> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Task> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the tasks by userId.
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdWithEagerRelationships(Long userId, Pageable pageable);

    /**
     * Get all the tasks by userId.
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserId(Long userId, Pageable pageable);

    /**
     * Get all the tasks by title.
     *
     * @param title
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndTitle(Long userId, String title, Pageable pageable);

    /**
     * Get all the tasks by title.
     *
     * @param title
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndTitleWithEagerRelationships(Long userId, String title, Pageable pageable);
}