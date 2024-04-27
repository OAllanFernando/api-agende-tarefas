package com.task.manager.domain;

import static com.task.manager.domain.TagTestSamples.*;
import static com.task.manager.domain.TaskTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.task.manager.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = getTaskSample1();
        Task task2 = new Task();
        assertThat(task1).isNotEqualTo(task2);

        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);

        task2 = getTaskSample2();
        assertThat(task1).isNotEqualTo(task2);
    }

    @Test
    void tagTest() throws Exception {
        Task task = getTaskRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        task.addTag(tagBack);
        assertThat(task.getTags()).containsOnly(tagBack);

        task.removeTag(tagBack);
        assertThat(task.getTags()).doesNotContain(tagBack);

        task.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(task.getTags()).containsOnly(tagBack);

        task.setTags(new HashSet<>());
        assertThat(task.getTags()).doesNotContain(tagBack);
    }
}
