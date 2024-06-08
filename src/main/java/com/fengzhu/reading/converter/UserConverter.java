package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.User;
import com.fengzhu.reading.dto.UserDTO;

public class UserConverter {

    public static User converToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .salt(userDTO.getSalt())
                .build();
    }
}
