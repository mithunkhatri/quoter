package com.mithunkhatri.quoter.repositories;

import com.mithunkhatri.quoter.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT distinct tagName FROM Tag")
    List<String> findAllTags();
}
