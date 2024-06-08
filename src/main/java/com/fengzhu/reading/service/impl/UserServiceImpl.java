package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.UserConverter;
import com.fengzhu.reading.dataObject.User;
import com.fengzhu.reading.dto.UserDTO;
import com.fengzhu.reading.repository.UserRepository;
import com.fengzhu.reading.service.UserService;
import com.fengzhu.reading.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public long registerUser(UserDTO userDTO) {
        User user = UserConverter.converToUser(userDTO);
        String password = userDTO.getPassword();
        String salt = userDTO.getSalt();
        String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        user.setPassword(md5Password);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public String login(String userName, String password) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("userName:" + userName + " not found"));

        String md5Password = DigestUtils.md5DigestAsHex((password + user.getSalt()).getBytes());
        if (!md5Password.equals(user.getPassword())) {
            throw new IllegalArgumentException("username and password not match");
        }
        return jwtUtils.getToken(String.valueOf(user.getId()), user.getUsername());

    }
}
