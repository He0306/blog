package com.hc.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<UserListVo> userListVos;

    private long total;
}
