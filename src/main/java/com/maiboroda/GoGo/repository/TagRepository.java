package com.maiboroda.GoGo.repository;

import com.maiboroda.GoGo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
