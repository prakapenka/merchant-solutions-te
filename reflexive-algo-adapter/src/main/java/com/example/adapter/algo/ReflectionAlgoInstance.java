package com.example.adapter.algo;

import com.example.domain.AlgoInstance;
import com.example.domain.AlgoOperations;
import com.example.domain.Command;
import com.example.domain.StringCommand;
import com.example.domain.TwoIntegersCommand;
import com.example.domain.VoidCommand;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReflectionAlgoInstance implements AlgoInstance {

  private final Class<?> klass;
  private final Object algo;

  @Override
  public void invoke(Command command) {
    try {
      invokeMethod(command);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException("unable to perform command " + command, e);
    }
  }

  private void invokeMethod(Command command)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    var methodName = translateAlgoOperation(command.getOperation());

    /*
    NOTE java 17 supports pattern matching for case expression only in "preview mode"
    While awesome Scala for example supports it for years - https://docs.scala-lang.org/tour/pattern-matching.html
    ... or also Kotlin....
    ... so rewrite next code after September 2023 when LTS 21 will be released
    ... something like:
    switch (command) {
      case VoidCommand v : {...}
    */
    if (command instanceof VoidCommand) {
      Method methodHandler = this.klass.getMethod(methodName);
      if (!methodHandler.canAccess(algo)) {
        methodHandler.setAccessible(true);
      }
      methodHandler.invoke(algo);
    } else if (command instanceof TwoIntegersCommand ti) {
      // ask directly for 2 integers.
      // as alternative, we could store Type[] information for every command to use more generic way
      // searching for method signatures. For simplicity, we guess type information base on command class name.
      Method methodHandler = this.klass.getMethod(methodName, int.class, int.class);
      if (!methodHandler.canAccess(algo)) {
        methodHandler.setAccessible(true);
      }
      // generic way to pass parameters to method:
      Object[] arr = new Object[]{ti.getA(), ti.getB()};
      methodHandler.invoke(algo, arr);
    } else if (command instanceof StringCommand) {
      // we don't have method invocations that supports string input
      throw new RuntimeException("String input not supported!");
    } else {
      throw new RuntimeException("Unsupported command: " + command);
    }
  }

  // here we coupled with method names from Algo class
  private String translateAlgoOperation(AlgoOperations operation) {
    return switch (operation) {
      case DO_ALGO -> "doAlgo";
      case CANCEL_THREADS -> "cancelTrades";
      case REVERSE -> "reverse";
      case SUBMIT_TO_MARKET -> "submitToMarket";
      case PERFORM_CALC -> "performCalc";
      case SETUP -> "setUp";
      case SET_ALGO_PARAM -> "setAlgoParam";
      case MAGIC -> throw new RuntimeException("Magic is not supported!");
    };
  }
}
