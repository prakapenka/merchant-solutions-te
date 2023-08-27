package com.example.adapter.algo;

import com.example.domain.AlgoInstanceFactory;
import com.example.domain.Command;
import com.example.domain.HandleAlgoCommandsPort;
import com.example.domain.exception.AlgoInvocationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlgoAdapter implements HandleAlgoCommandsPort {

  final AlgoInstanceFactory factory;

  public void handleAlgoCommand(List<Command> commands) throws AlgoInvocationException {
    var algo = factory.getAlgoInstance();
    algo.invokeAll(commands);
  }
}
