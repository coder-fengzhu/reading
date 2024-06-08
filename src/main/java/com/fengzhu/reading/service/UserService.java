package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.UserDTO;

public interface UserService {

    long registerUser(UserDTO userDTO);

    String login(String username, String password);
}
