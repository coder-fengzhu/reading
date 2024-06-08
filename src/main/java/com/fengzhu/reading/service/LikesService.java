package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.LikesUserRecordDTO;

import java.util.List;

public interface LikesService {

    boolean addNewLikesRecord(LikesUserRecordDTO likesUserRecordDTO);

    List<Long> getMyLikes(long userId, Long businessId);

    Long getItemLikesCount(Long businessId, Long itemId);
}
