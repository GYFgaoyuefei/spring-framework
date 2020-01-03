package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class CacheRemoveVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
   private Set<String> keys;
    
}
