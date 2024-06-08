package com.fengzhu.reading.validator;

import com.fengzhu.reading.dto.LikesUserRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class LikesValidator {

    public void validateAddNewBook(LikesUserRecordDTO likesUserRecordDTO) {
        if (likesUserRecordDTO.getUserId() == 0L) {
            throw new IllegalArgumentException("userId is empty");
        }
        if (likesUserRecordDTO.getBusinessId() == 0L) {
            throw new IllegalArgumentException("businessId is empty");
        }

        if (likesUserRecordDTO.getItemId() == 0L) {
            throw new IllegalArgumentException("itemId is empty");
        }


    }
}
