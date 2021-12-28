package com.feliper.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class UserDto {

    private Integer id;
    private String name;
    private Integer balance;

}
