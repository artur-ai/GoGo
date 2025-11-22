package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TagServiceITest extends AbstractIntegrationTest {

    @Autowired
    private TagService tagService;

    @Test
    void testGetAllTags() {
        List<Tag> tags = tagService.getAllTags();

        assertFalse(tags.isEmpty());
        assertEquals(8, tags.size());
    }

    @Test
    void testFindTagsById() {
        List<Long> tags = List.of(1L, 3l);
        List<Tag> foundTags = tagService.findTagsByIds(tags);

        assertEquals(2, foundTags.size());
    }
}
