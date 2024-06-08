package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.TagGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long>, JpaSpecificationExecutor<TagGroup> {


}
