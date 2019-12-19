package com.eseasky.protocol.picture.entity.VO;

import lombok.Data;

import java.util.HashMap;

/**
 * @author zhanghai by 2019/9/23
 */
@Data
public class NewFileResourceInfo {

    private HashMap<String, byte[]> param;
    private String fileName;

}
