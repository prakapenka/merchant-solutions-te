package com.example.domain;

import com.example.domain.exception.AlgoInvocationException;

public interface HandleSignalPort {

  void handleSignal(int signal) throws AlgoInvocationException;
}
