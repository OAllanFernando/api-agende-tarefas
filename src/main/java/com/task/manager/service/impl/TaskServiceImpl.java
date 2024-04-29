package com.task.manager.service.impl;

import com.task.manager.domain.Task;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import java.util.Optional;
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
}
