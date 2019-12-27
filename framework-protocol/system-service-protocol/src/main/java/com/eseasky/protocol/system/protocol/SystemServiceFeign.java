package com.eseasky.protocol.system.protocol;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.global.entity.MsgReturn;
import com.eseasky.protocol.system.entity.DTO.DictiCondiDTO;
import com.eseasky.protocol.system.entity.VO.DictItemVO;
import com.eseasky.protocol.system.entity.VO.DictionaryVO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Feign(serviceName="SystemService")
public interface SystemServiceFeign {

    /*@RequestLine("POST /dict/queryByKeyAndDictId")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    public ResponseEntity<MsgReturn<DictItemVO>> queryByKeyAndDictId(@RequestBody DictiCondiDTO dictiCondiDTO);*/


    @RequestLine("POST /dict/queryByTypeAndGroup")
    @Headers({"Content-Type: application/json","Accept: application/json"})
	public ResponseEntity<MsgReturn<DictionaryVO>> queryByTypeAndGroup(@RequestBody DictiCondiDTO dictiCondiDTO);

}
