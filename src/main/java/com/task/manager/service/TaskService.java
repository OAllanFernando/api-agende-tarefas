package com.task.manager.service;

import com.task.manager.domain.Tag;
import com.task.manager.domain.Task;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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

    /**
     * Get all the tasks by day.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTimeWithEagerRelationships(Long userId, int year, int month, int day, Pageable pageable);

    /**
     * Get all the tasks by day.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTime(Long userId, int year, int month, int day, Pageable pageable);

    /**
     * Get all the tasks by week.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTimeByWeek(Long userId, Instant startDate, Instant endDate, Pageable pageable);

    /**
     * Get all the tasks by week.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTimeByWeekWithEagerRelationships(
        Long userId,
        Instant startDate,
        Instant endDate,
        Pageable pageable
    );

    /**
     * Get all the tasks by month.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTimeByMonth(Long userId, int year, int month, Pageable pageable);

    /**
     * Get all the tasks by month.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    Page<Task> findAllByUserIdAndExecutionTimeByMonthWithEagerRelationships(Long userId, int year, int month, Pageable pageable);

    /**
     * Update tags of the task.
     *
     * @param executionTime
     * @param pageable
     * @return
     */
    void updateTags(Long taskId, List<Tag> tags);

    /**
     * Get all the tasks by userId for report of solved and non solved.
     *
     * @param userId
     * @param pageable
     * @return
     */
    Object getTasksForRel(Long userId);

    /**
     * Get all the tasks by userId for report of solved and non solved.
     *
     * @param userId
     * @param pageable
     * @return
     */
    Object countResolvedTasksByTag(Long userId);
}
