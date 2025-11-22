package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Tag;
import com.maiboroda.GoGo.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    public List<Tag> findTagsByIds(List<Long> tags) {
        return tagRepository.findAllById(tags);
    }
}