package com.task.manager.service;

import com.task.manager.domain.Tag;
import com.task.manager.domain.Task;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.task.manager.domain.Tag}.
 */
public interface TagService {
    /**
     * Save a tag.
     *
     * @param tag the entity to save.
     * @return the persisted entity.
     */
    Tag save(Tag tag);

    /**
     * Updates a tag.
     *
     * @param tag the entity to update.
     * @return the persisted entity.
     */
    Tag update(Tag tag);

    /**
     * Partially updates a tag.
     *
     * @param tag the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tag> partialUpdate(Tag tag);

    /**
     * Get all the tags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tag> findOne(Long id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
     * Get all the tags by user id.
     *
     * @param userId   the id of the user.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    // Page<Tag> findAllByUserId(Long userId, Pageable pageable);
}
