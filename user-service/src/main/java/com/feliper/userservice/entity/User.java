package com.feliper.userservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Data
@Builder
@Table("users")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer balance;
}
