package com.fengzhu.reading.controller;

import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.LikesUserRecordDTO;
import com.fengzhu.reading.service.LikesService;
import com.fengzhu.reading.utils.BaseContext;
import com.fengzhu.reading.validator.LikesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikesController {

    @Autowired
    private LikesService likesService;

    @Autowired
    private LikesValidator likesValidator;

    @PostMapping("/likes")
    public CommonResponse<Boolean> addlikeItem(@RequestBody LikesUserRecordDTO likesUserRecordDTO) {
        likesValidator.validateAddNewBook(likesUserRecordDTO);
        return CommonResponse.newSuccess(likesService.addNewLikesRecord(likesUserRecordDTO));
    }

    @GetMapping("/likes/{businessId}")
    public CommonResponse<List<Long>> getMyLikes(@PathVariable Long businessId) {
        long userId = BaseContext.getCurrentId();
        return CommonResponse.newSuccess(likesService.getMyLikes(userId, businessId));
    }

    @GetMapping("/likes/{businessId}/{itemId}")
    public CommonResponse<Long> getItemLikesCount(@PathVariable Long businessId, @PathVariable Long itemId) {
        return CommonResponse.newSuccess(likesService.getItemLikesCount(businessId, itemId));
    }

}
