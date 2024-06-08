package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.TagDTO;
import com.fengzhu.reading.dto.TagGroupDTO;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagDTO addNewTag(TagDTO tagDTO);

    TagDTO getTagDTOByTagId(Long tagId);

    Map<TagGroupDTO, List<TagDTO>> getAllTags();
}
