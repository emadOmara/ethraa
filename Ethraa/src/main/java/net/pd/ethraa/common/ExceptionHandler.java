package net.pd.ethraa.common;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EthraaExceptionHandlerAdvice {

    private static Logger logger = LoggerFactory.getLogger(EthraaExceptionHandlerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exception(Exception e) {
	logger.error(e.getMessage(), e);
	String jsonString = "{\"success\": 0, \"comment\": \"" + StringEscapeUtils.escapeJson(e.getMessage()) + "\" }";

	return jsonString;
    }
}