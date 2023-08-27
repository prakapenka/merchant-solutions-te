package com.example.domain;

import com.example.domain.exception.AlgoInvocationException;
import java.util.List;

public interface AlgoInstance {

  void invoke(Command command) throws AlgoInvocationException;

  default void invokeAll(List<Command> commands) throws AlgoInvocationException {
    for (Command c : commands) {
      invoke(c);
    }
  }
}
