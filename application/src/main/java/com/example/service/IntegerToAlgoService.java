package com.example.service;

import com.example.domain.AlgoOperations;
import com.example.domain.Command;
import com.example.domain.HandleAlgoCommandsPort;
import com.example.domain.HandleSignalPort;
import com.example.domain.TwoIntegersCommand;
import com.example.domain.VoidCommand;
import com.example.domain.exception.AlgoInvocationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegerToAlgoService implements HandleSignalPort {

  // injected from library that provides this interface implementation
  // see reflexive-algo-adapter module
  private final HandleAlgoCommandsPort handleCommandsPort;

  public void handleSignal(int i) throws AlgoInvocationException {
    var commands = getCommandList(i);
    handleCommandsPort.handleAlgoCommand(commands);
  }

  // will be refactored to keep command in some flexible persistence unit
  List<Command> getCommandList(int i) {
    return switch (i) {
      case 1 -> List.of(
          new VoidCommand(AlgoOperations.SETUP),
          new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 60),
          new VoidCommand(AlgoOperations.PERFORM_CALC),
          new VoidCommand(AlgoOperations.SUBMIT_TO_MARKET),
          new VoidCommand(AlgoOperations.DO_ALGO)
      );
      case 2 -> List.of(
          new VoidCommand(AlgoOperations.REVERSE),
          new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 80),
          new VoidCommand(AlgoOperations.SUBMIT_TO_MARKET),
          new VoidCommand(AlgoOperations.DO_ALGO)
      );
      case 3 -> List.of(
          new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 90),
          new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 2, 15),
          new VoidCommand(AlgoOperations.PERFORM_CALC),
          new VoidCommand(AlgoOperations.SUBMIT_TO_MARKET),
          new VoidCommand(AlgoOperations.DO_ALGO)
      );
      default -> List.of(
          new VoidCommand(AlgoOperations.CANCEL_THREADS),
          new VoidCommand(AlgoOperations.DO_ALGO)
      );
    };
  }
}
