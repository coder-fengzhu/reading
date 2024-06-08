package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.LikesUserRecord;
import com.fengzhu.reading.dto.LikesUserRecordDTO;

public class LikesUserRecordConverter {

    public static LikesUserRecord convertToLikesUserRecord(LikesUserRecordDTO likesUserRecordDTO) {
        if (likesUserRecordDTO == null) {
            return null;
        }

        return LikesUserRecord.builder()
                .id(likesUserRecordDTO.getId())
                .businessId(likesUserRecordDTO.getBusinessId())
                .itemId(likesUserRecordDTO.getItemId())
                .likes(likesUserRecordDTO.isLikes())
                .userId(likesUserRecordDTO.getUserId())
                .build();
    }
}
