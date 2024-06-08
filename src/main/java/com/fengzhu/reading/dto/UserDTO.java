package com.fengzhu.reading.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String username;

    private String password;

    private String email;

    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-", "");

}
