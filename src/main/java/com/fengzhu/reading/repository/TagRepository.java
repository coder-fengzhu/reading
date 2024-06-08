package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query(value = "select max(tag_value) from tag where tag_group_id = :tagGroupId", nativeQuery = true)
    Optional<Long> findMaxTagInGroup(@RequestParam("tagGroupId") long tagGroupId);

    List<Tag> findByIdIn(List<Long> ids);

    List<Tag> findByTagGroupId(long tagGroupId);
}
