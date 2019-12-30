package com.eseasky.protocol.system.protocol.hystrix;

import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;
import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.global.entity.MsgReturn;
import com.eseasky.protocol.system.entity.DTO.DictiCondiDTO;
import com.eseasky.protocol.system.entity.VO.DictionaryVO;
import com.eseasky.protocol.system.protocol.SystemServiceFeign;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j2
public class SystemServiceFeignHystrix implements SystemServiceFeign, IHystrix {

    private Throwable throwable;

    @Override
    public Throwable setThrowable(Throwable throwable) {
        // TODO Auto-generated method stub
        log.error(throwable.getMessage());
        return throwable;
    }

    /*@Override
    public ResponseEntity<MsgReturn<DictItemVO>> queryByKeyAndDictId(DictiCondiDTO dictiCondiDTO) {
        return null;
    }*/

    @Override
    public ResponseEntity<MsgReturn<DictionaryVO>> queryByTypeAndGroup(DictiCondiDTO dictiCondiDTO) {
        throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
    }

    @Override
    public ResponseEntity<MsgReturn<Map<DictiCondiDTO,String>>> queryListByTypeAndGroup(@RequestBody Set<DictiCondiDTO> dictiCondiDTOS){
        throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
    }
}
