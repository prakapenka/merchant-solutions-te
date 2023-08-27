package com.example.adapter.algo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.domain.AlgoOperations;
import com.example.domain.Command;
import com.example.domain.TwoIntegersCommand;
import com.example.domain.VoidCommand;
import com.example.domain.exception.AlgoInvocationException;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class AlgoAdapterTest {

  private AlgoAdapter adapter;

  private static Stream<Arguments> provideValidAccountCreationRequests() {
    Object[][] data = new Object[][]{
        {new VoidCommand(AlgoOperations.DO_ALGO), "doAlgo"},
        {new VoidCommand(AlgoOperations.CANCEL_THREADS), "cancelTrades"},
        {new VoidCommand(AlgoOperations.REVERSE), "reverse"},
        {new VoidCommand(AlgoOperations.SUBMIT_TO_MARKET), "submitToMarket"},
        {new VoidCommand(AlgoOperations.PERFORM_CALC), "performCalc"},
        {new VoidCommand(AlgoOperations.SETUP), "setUp"},
        {new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 99), "setAlgoParam 1,99"}
    };
    return Stream.of(data)
        .map(d -> Arguments.of(d[0], d[1]));
  }

  @BeforeEach
  void beforeEach() {
    var factory = new ReflectionAlgoInstanceFactory();
    factory.init();
    this.adapter = new AlgoAdapter(factory);
  }

  @ParameterizedTest
  @MethodSource("provideValidAccountCreationRequests")
  void testVoidCommands(Command command, String expected, CapturedOutput co)
      throws AlgoInvocationException {
    adapter.handleAlgoCommand(List.of(command));
    var actual = StringUtils.normalizeSpace(
        co.getOut()); // different platforms could have different line endings
    assertEquals(expected, actual);
  }
}
