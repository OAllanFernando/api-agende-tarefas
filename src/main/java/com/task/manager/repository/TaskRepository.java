package com.task.manager.repository;

import com.task.manager.domain.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 *
 * When extending this class, extend TaskRepositoryWithBagRelationships too.
 * For more information refer to
 * https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TaskRepository extends TaskRepositoryWithBagRelationships, JpaRepository<Task, Long> {
    @Query("select task from Task task where task.user.login = ?#{authentication.name}")
    List<Task> findByUserIsCurrentUser();

    default Optional<Task> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Task> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Task> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    default Page<Task> findAllByUserIdWithEagerRelationships(Long userId, Pageable pageable) {
        return this.fetchBagRelationships(this.findAllByUserId(userId, pageable));
    }

    Page<Task> findAllByUserId(Long userId, Pageable pageable);

    Page<Task> findAllByUserIdAndTitleContaining(Long userId, String title, Pageable pageable);

    default Page<Task> findAllByUserIdAndTitleWithEagerRelationships(Long userId, String title, Pageable pageable) {
        return this.fetchBagRelationships(this.findAllByUserIdAndTitleContaining(userId, title, pageable));
    }
}
