package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.TagConverter;
import com.fengzhu.reading.converter.TagGroupConverter;
import com.fengzhu.reading.repository.TagGroupRepository;
import com.fengzhu.reading.repository.TagRepository;
import com.fengzhu.reading.dataObject.Tag;
import com.fengzhu.reading.dataObject.TagGroup;
import com.fengzhu.reading.dto.TagDTO;
import com.fengzhu.reading.dto.TagGroupDTO;
import com.fengzhu.reading.service.TagService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagGroupRepository tagGroupRepository;

    @Override
    public TagDTO addNewTag(TagDTO tagDTO) {
        Tag tag = TagConverter.convertToTag(tagDTO);

        long maxTagValue = tagRepository.findMaxTagInGroup(tagDTO.getTagGroupId()).orElse(0L);
        if (maxTagValue >= (Long.MAX_VALUE)) {
            throw new UnsupportedOperationException("tagGroupId:" + tagDTO.getTagGroupId() + " 下tag数量已满");
        }
        tag.setTagValue(maxTagValue == 0L ? 1L : maxTagValue << 1);
        tag = tagRepository.save(tag);
        return TagConverter.convertToTagDTO(tag);
    }

    @Override
    public TagDTO getTagDTOByTagId(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("tagId:" + tagId + " not found"));

        return TagConverter.convertToTagDTO(tag);
    }

    @Override
    public Map<TagGroupDTO, List<TagDTO>> getAllTags() {
        List<TagGroup> tagGroupList = tagGroupRepository.findAll();
        List<TagGroupDTO> tagGroupDTOList = tagGroupList.stream()
                .map(TagGroupConverter::converToTagGroupDTO)
                .collect(Collectors.toList());

        Map<TagGroupDTO, List<TagDTO>> map = Maps.newHashMap();
        for (TagGroupDTO tagGroupDTO : tagGroupDTOList) {
            List<Tag> tagList = tagRepository.findByTagGroupId(tagGroupDTO.getTagGroupId());

            if (CollectionUtils.isEmpty(tagList)) {
                continue;
            }

            List<TagDTO> tagDTOS = tagList.stream().map(TagConverter::convertToTagDTO).collect(Collectors.toList());
            map.put(tagGroupDTO, tagDTOS);
        }
        return map;
    }
}
