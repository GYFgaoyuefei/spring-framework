package com.eseasky.protocol.system.protocol.hystrix;

import com.eseasky.global.entity.MsgReturn;
import com.eseasky.protocol.system.entity.DTO.DictiCondiDTO;
import com.eseasky.protocol.system.entity.VO.DictItemVO;
import com.eseasky.protocol.system.protocol.SystemServiceFeign;
import org.springframework.http.ResponseEntity;

public class SystemServiceFeignHystrix implements SystemServiceFeign {
    @Override
    public ResponseEntity<MsgReturn<DictItemVO>> queryByKeyAndDictId(DictiCondiDTO dictiCondiDTO) {
        return null;
    }
}
