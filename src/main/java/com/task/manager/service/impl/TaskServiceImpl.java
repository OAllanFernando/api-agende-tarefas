package com.task.manager.service.impl;

import com.task.manager.domain.Tag;
import com.task.manager.domain.Task;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.task.manager.domain.Task}.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("Request to update Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> partialUpdate(Task task) {
        log.debug("Request to partially update Task : {}", task);

        return taskRepository
            .findById(task.getId())
            .map(existingTask -> {
                if (task.getTitle() != null) {
                    existingTask.setTitle(task.getTitle());
                }
                if (task.getDescription() != null) {
                    existingTask.setDescription(task.getDescription());
                }
                if (task.getExecutionTime() != null) {
                    existingTask.setExecutionTime(task.getExecutionTime());
                }
                if (task.getDurationMin() != null) {
                    existingTask.setDurationMin(task.getDurationMin());
                }
                if (task.getClosed() != null) {
                    existingTask.setClosed(task.getClosed());
                }

                return existingTask;
            })
            .map(taskRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll(pageable);
    }

    public Page<Task> findAllWithEagerRelationships(Pageable pageable) {
        return taskRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }

    @Override
    public Page<Task> findAllByUserIdWithEagerRelationships(Long userId, Pageable pageable) {
        log.debug("Request to get all Tasks by userId");
        return taskRepository.findAllByUserIdWithEagerRelationships(userId, pageable);
    }

    @Override
    public Page<Task> findAllByUserId(Long userId, Pageable pageable) {
        log.debug("Request to get all Tasks by userId");
        return taskRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndTitle(Long userId, String title, Pageable pageable) {
        log.debug("Request to get all Tasks by title");
        return taskRepository.findAllByUserIdAndTitleContaining(userId, title, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndTitleWithEagerRelationships(Long userId, String title, Pageable pageable) {
        log.debug("Request to get all Tasks by title");
        return taskRepository.findAllByUserIdAndTitleWithEagerRelationships(userId, title, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTime(Long userId, int year, int month, int day, Pageable pageable) {
        log.debug("Request to get all Tasks by day");
        return taskRepository.findAllByUserIdAndExecutionTime(userId, year, month, day, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTimeWithEagerRelationships(Long userId, int year, int month, int day, Pageable pageable) {
        log.debug("Request to get all Tasks by day");
        return taskRepository.findAllByUserIdAndExecutionTimeWithEagerRelationships(userId, year, month, day, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTimeByWeek(Long userId, Instant startDate, Instant endDate, Pageable pageable) {
        log.debug("Request to get all Tasks by week");
        return taskRepository.findAllByUserIdAndExecutionTimeByWeek(userId, startDate, endDate, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTimeByWeekWithEagerRelationships(
        Long userId,
        Instant startDate,
        Instant endDate,
        Pageable pageable
    ) {
        log.debug("Request to get all Tasks by week");
        return taskRepository.findAllByUserIdAndExecutionTimeByWeekWithEagerRelationships(userId, startDate, endDate, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTimeByMonth(Long userId, int year, int month, Pageable pageable) {
        log.debug("Request to get all Tasks by month");
        return taskRepository.findAllByUserIdAndExecutionTimeByMonth(userId, year, month, pageable);
    }

    @Override
    public Page<Task> findAllByUserIdAndExecutionTimeByMonthWithEagerRelationships(Long userId, int year, int month, Pageable pageable) {
        log.debug("Request to get all Tasks by month");
        return taskRepository.findAllByUserIdAndExecutionTimeByMonthWithEagerRelationships(userId, year, month, pageable);
    }

    @Override
    public void updateTags(Long taskId, List<Tag> tags) {
        log.debug("Request to update Task tags : {}", taskId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        Set<Tag> tagsToUpdate = new HashSet<>(tags);

        // Atualizar as tags da tarefa
        task.setTags(tagsToUpdate);

        // Salvar a tarefa atualizada no banco de dados
        taskRepository.save(task);
    }
}
