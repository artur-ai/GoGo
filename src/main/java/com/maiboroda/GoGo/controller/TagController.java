package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.entity.Tag;
import com.maiboroda.GoGo.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }
}