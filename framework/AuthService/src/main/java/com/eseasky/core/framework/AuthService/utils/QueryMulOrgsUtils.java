package com.eseasky.core.framework.AuthService.utils;

import com.eseasky.global.utils.SequeceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin_z by 2019/12/24
 * @ClassName QueryMulOrgsUtils
 */
public class QueryMulOrgsUtils {

    public static Set<String> getOrgCodes(List<String> codes){
        //过滤所有一级组织
        Set<String> firstCode = codes.stream().filter(item -> SequeceHelper.getLevel(item) == 1).collect(Collectors.toSet());

        //获取所有非一级组织
        codes.removeAll(firstCode);

        codes.forEach(item->{
            if (!isOrgCodeExist(item,firstCode)){
                firstCode.add(item);
            }
        });
        return firstCode;
    }

    private static boolean isOrgCodeExist(String secCode,Set<String> firstCode){
        boolean flag = false;
        for (String code : firstCode) {
            if (secCode.indexOf(code) == 0){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {{
            add("001");
            add("002");
            add("003");
            add("0010001");
            add("0020003");
            add("0040003000123");
        }};

        getOrgCodes(list);
    }
}
