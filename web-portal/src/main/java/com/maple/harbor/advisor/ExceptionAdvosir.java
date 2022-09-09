package com.maple.harbor.advisor;

import com.maple.harbor.exception.BizException;
import com.maple.harbor.result.MvcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ExceptionAdvosir {

    @ExceptionHandler(value = BizException.class)
    public MvcResult<String> bizError(BizException bizException) {
        log.error("业务异常, 信息为: " + bizException.getMessage());
        return MvcResult.fail(bizException.getMessage());
    }

}
