package com.example.service;

import com.example.adapter.algo.AlgoAdapter;
import com.example.adapter.algo.ReflectionAlgoInstanceFactory;
import com.example.domain.HandleAlgoCommandsPort;
import com.example.domain.HandleSignalPort;
import com.example.domain.exception.AlgoInvocationException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;

@ExtendWith(OutputCaptureExtension.class)
public class IntegerToAlgoOutputTest {

  private HandleSignalPort signalServiceAdapter;

  @BeforeEach
  void beforeEach() {
    ReflectionAlgoInstanceFactory factory = new ReflectionAlgoInstanceFactory();
    var initMethod = ReflectionUtils.findMethod(ReflectionAlgoInstanceFactory.class, "init");
    Objects.requireNonNull(initMethod, "init method must not be null");
    initMethod.setAccessible(true);
    ReflectionUtils.invokeMethod(initMethod, factory);
    HandleAlgoCommandsPort port = new AlgoAdapter(factory);
    this.signalServiceAdapter = new IntegerToAlgoService(port);
  }

  @Test
  public void test_1(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/1.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_2(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(2);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/2.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_3(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(3);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/3.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_default(CapturedOutput co) throws IOException, AlgoInvocationException {
    // assume we don't support -1 integer
    signalServiceAdapter.handleSignal(-1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/default.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  // support different line endings platforms
  private void assertEquals(String expected, String actual) {
    org.junit.jupiter.api.Assertions.assertEquals(
        StringUtils.normalizeSpace(expected),
        StringUtils.normalizeSpace(actual)
    );
  }
}
