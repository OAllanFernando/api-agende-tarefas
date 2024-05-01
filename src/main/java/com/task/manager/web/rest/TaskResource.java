package com.task.manager.web.rest;

import com.task.manager.domain.Tag;
import com.task.manager.domain.Task;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import com.task.manager.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.task.manager.domain.Task}.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "task";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskService taskService;

    private final TaskRepository taskRepository;

    public TaskResource(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    /**
     * {@code POST  /tasks} : Create a new task.
     *
     * @param task the task to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new task, or with status {@code 400 (Bad Request)} if the
     *         task has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to save Task : {}", task);
        if (task.getId() != null) {
            throw new BadRequestAlertException("A new task cannot already have an ID", ENTITY_NAME, "idexists");
        }
        System.out.println("Task: " + task.getTags());

        Task result = taskService.save(task);
        return ResponseEntity
            .created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/:id} : Updates an existing task.
     *
     * @param id   the id of the task to save.
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated task,
     *         or with status {@code 400 (Bad Request)} if the task is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the task
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id", required = false) final Long id, @RequestBody Task task)
        throws URISyntaxException {
        log.debug("REST request to update Task : {}, {}", id, task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, task.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Task result = taskService.update(task);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tasks/:id} : Partial updates given fields of an existing task,
     * field will ignore if it is null
     *
     * @param id   the id of the task to save.
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated task,
     *         or with status {@code 400 (Bad Request)} if the task is not valid,
     *         or with status {@code 404 (Not Found)} if the task is not found,
     *         or with status {@code 500 (Internal Server Error)} if the task
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Task> partialUpdateTask(@PathVariable(value = "id", required = false) final Long id, @RequestBody Task task)
        throws URISyntaxException {
        log.debug("REST request to partial update Task partially : {}, {}", id, task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, task.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Task> result = taskService.partialUpdate(task);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, task.getId().toString())
        );
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks");
        Page<Task> page;
        if (eagerload) {
            page = taskService.findAllWithEagerRelationships(pageable);
        } else {
            page = taskService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" task.
     *
     * @param id the id of the task to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the task, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        log.debug("REST request to get Task : {}", id);
        Optional<Task> task = taskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(task);
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        log.debug("REST request to delete Task : {}", id);
        taskService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /tasks} : get all the tasks by the user.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */
    @GetMapping("/user-tasks/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByUser(
        @PathVariable Long userId,
        @ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks for user with ID: {}", userId);
        Page<Task> page;
        if (eagerload) {
            page = taskService.findAllByUserIdWithEagerRelationships(userId, pageable);
        } else {
            page = taskService.findAllByUserId(userId, pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks} : get all the tasks by title.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */
    @GetMapping("/tasks-by-title/{title}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByTitle(
        @PathVariable String title,
        @PathVariable Long userId,
        @ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks with title: {}", title);
        Page<Task> page;
        if (eagerload) {
            page = taskService.findAllByUserIdAndTitleWithEagerRelationships(userId, title, pageable);
        } else {
            page = taskService.findAllByUserIdAndTitle(userId, title, pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks} : get all the tasks by day
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */

    @GetMapping("/tasks-by-day/{day}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByDay(
        @PathVariable String day,
        @PathVariable Long userId,
        @ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks with day: {}", day);

        LocalDateTime localDateTime;
        try {
            // Formate a string 'day' para LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(day, formatter);
            localDateTime = parsedDate.atStartOfDay();
        } catch (DateTimeParseException e) {
            // Se não for possível analisar a data, retorne um erro 400 Bad Request
            return ResponseEntity.badRequest().build();
        }

        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();

        System.out.println("year: " + year + " month: " + month + " dayOaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafMonth: " + dayOfMonth);

        Page<Task> page;
        if (eagerload) {
            page = taskService.findAllByUserIdAndExecutionTimeWithEagerRelationships(userId, year, month, dayOfMonth, pageable);
        } else {
            page = taskService.findAllByUserIdAndExecutionTime(userId, year, month, dayOfMonth, pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks} : get all the tasks by week
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */
    @GetMapping("/tasks-by-week/{week}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByWeek(
        @PathVariable String week,
        @PathVariable Long userId,
        @ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks with week: {}", week);

        try {
            String weekString = week; // Formato: "2021-W01"

            int year = Integer.parseInt(weekString.substring(0, 4));
            int weekNumber = Integer.parseInt(weekString.substring(6));

            // pega a data de inicio da semana
            LocalDate startDate = LocalDate
                .ofYearDay(year, 1)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), weekNumber)
                .with(DayOfWeek.MONDAY);

            // pega a data de termino da semana
            LocalDate endDate = startDate.plusDays(6);

            Instant startDateInstant = startDate.atStartOfDay(ZoneId.systemDefault()).with(LocalTime.MIN).toInstant();
            Instant endDateInstant = endDate
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(-1)
                .with(LocalTime.MAX)
                .minusSeconds(1)
                .toInstant();

            System.out.println("Data de início da semana: " + startDateInstant);
            System.out.println("Data de término da semana: " + endDateInstant);
            Page<Task> page;
            if (eagerload) {
                page =
                    taskService.findAllByUserIdAndExecutionTimeByWeekWithEagerRelationships(
                        userId,
                        startDateInstant,
                        endDateInstant,
                        pageable
                    );
            } else {
                page = taskService.findAllByUserIdAndExecutionTimeByWeek(userId, startDateInstant, endDateInstant, pageable);
            }
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * {@code GET  /tasks} : get all the tasks by month
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tasks in body.
     */
    @GetMapping("/tasks-by-month/{month}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByMonth(
        @PathVariable String month,
        @PathVariable Long userId,
        @ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tasks with month: {}", month);

        try {
            String monthString = month; // Formato: "2021-01"

            int year = Integer.parseInt(monthString.substring(0, 4));
            int monthNumber = Integer.parseInt(monthString.substring(5));

            Page<Task> page;
            if (eagerload) {
                page = taskService.findAllByUserIdAndExecutionTimeByMonthWithEagerRelationships(userId, year, monthNumber, pageable);
            } else {
                page = taskService.findAllByUserIdAndExecutionTimeByMonth(userId, year, monthNumber, pageable);
            }

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{taskId}/update-tags")
    public ResponseEntity<Optional<Task>> updateTags(@PathVariable Long taskId, @RequestBody List<Tag> tags) {
        log.debug("REST request to update tags of Task : {}", taskId);
        taskService.updateTags(taskId, tags);
        Optional<Task> updatedTask = taskService.findOne(taskId); // Replace 'result' with the appropriate variable name
        return ResponseEntity.ok().body(updatedTask);
    }

    @GetMapping("/rel/{userId}")
    public ResponseEntity<Object> getTasksForRel(@PathVariable Long userId) {
        log.debug("REST request to get a page of Tasks for user with ID: {}", userId);
        Object tasks = taskService.getTasksForRel(userId);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/rel/{userId}/solved")
    public ResponseEntity<Object> countResolvedTasksByTag(@PathVariable Long userId) {
        log.debug("REST request to get a page of Tasks for user with ID: {}", userId);
        Object tasks = taskService.countResolvedTasksByTag(userId);
        return ResponseEntity.ok().body(tasks);
    }
}
