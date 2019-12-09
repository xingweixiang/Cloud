package com.cloudnative.security.oauth.dto;

import com.cloudnative.security.oauth.model.SysClient;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class SysClientDto extends SysClient {

    private static final long serialVersionUID = 1475637288060027265L;

    private List<Long> permissionIds;

    private Set<Long> serviceIds;

}
