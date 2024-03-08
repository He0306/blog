package com.hc.blog.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2023-02-11 22:53
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRetrieveDto implements Serializable {

    private String email;

    private String code;

    private String password;
}
