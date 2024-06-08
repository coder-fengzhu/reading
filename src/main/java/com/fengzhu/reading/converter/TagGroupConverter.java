package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.TagGroup;
import com.fengzhu.reading.dto.TagGroupDTO;

public class TagGroupConverter {

    public static TagGroupDTO converToTagGroupDTO(TagGroup tagGroup) {
        if (tagGroup == null) {
            return null;
        }

        return TagGroupDTO.builder()
                .tagGroupId(tagGroup.getId())
                .tagGroupName(tagGroup.getTagGroupName()).build();
    }
}
