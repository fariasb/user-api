package cl.ey.userapi.exception.handler;

import cl.ey.userapi.dto.BaseResponseDTO;
import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;
import cl.ey.userapi.type.ResultCode;
import cl.ey.userapi.util.MessageSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ UserEmailAlreadyRegisterException.class })
	public final BaseResponseDTO<Void> handleUserEmailAlreadyRegisterException(UserEmailAlreadyRegisterException exception) {
		log.error("handleUserEmailAlreadyRegisterException", exception);
		return new BaseResponseDTO<>(ResultCode.ERROR, MessageSourceUtil.getValue("msge.error.email.already.register"), null);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String msje = error.getDefaultMessage();
			if (msje != null && msje.contains(".")) {
				msje = MessageSourceUtil.getValue(error.getDefaultMessage());
			}
			errors.add(error.getField() + ": " + msje);
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		Collections.sort(errors);
		return new ResponseEntity<>(new BaseResponseDTO<>(ResultCode.ERROR, MessageSourceUtil.getValue("msge.error.bad.request"), errors), HttpStatus.BAD_REQUEST);

	}
}
