package com.example.service;

import com.example.domain.GetSignalCommandList;
import com.example.domain.HandleAlgoCommandsPort;
import com.example.domain.HandleSignalPort;
import com.example.domain.exception.AlgoInvocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegerToAlgoService implements HandleSignalPort {

  // injected from library that provides this interface implementation
  // see reflexive-algo-adapter module
  private final HandleAlgoCommandsPort handleCommandsPort;
  private final GetSignalCommandList signalCommandsPort;

  public void handleSignal(int i) throws AlgoInvocationException {
    var commands = signalCommandsPort.getCommandList(i);
    handleCommandsPort.handleAlgoCommand(commands);
  }

}
