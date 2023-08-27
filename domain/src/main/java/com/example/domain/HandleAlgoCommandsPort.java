package com.example.domain;

import com.example.domain.exception.AlgoInvocationException;
import java.util.List;

public interface HandleAlgoCommandsPort {

  void handleAlgoCommand(List<Command> commands) throws AlgoInvocationException;
}
