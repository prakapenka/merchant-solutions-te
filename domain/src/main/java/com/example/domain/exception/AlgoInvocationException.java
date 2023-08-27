package com.example.domain.exception;

public class AlgoInvocationException extends Exception {

  public AlgoInvocationException(String message, Throwable cause) {
    super(message, cause, true, true);
  }
}
