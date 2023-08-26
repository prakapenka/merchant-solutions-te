package com.example.adapter.rest.error;

import com.example.domain.exception.AlgoInvocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AlgoInvocationException.class)
  public ResponseEntity<ErrorDTO> handleAlgoInvocationException(AlgoInvocationException e) {
    log.error("Internal server error", e);
    return ResponseEntity.internalServerError().body(new ErrorDTO(e.getMessage()));
  }
}
