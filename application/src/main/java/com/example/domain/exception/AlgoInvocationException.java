package com.example.domain.exception;

public class AlgoInvocationException extends RuntimeException {

  public AlgoInvocationException(String message, Throwable cause) {
    super(message, cause, true, true);
  }
}
