package com.cloudnative.security.oauth.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
@Data
public class SysService implements Serializable {

    private static final long serialVersionUID = 749360940290141180L;

    private Long id;
    private Long parentId;
    private String name;
    private String css;
    private String url;
    private String path;
    private Integer sort;
    private Date createTime;
    private Date updateTime;

    private Integer isMenu;

}