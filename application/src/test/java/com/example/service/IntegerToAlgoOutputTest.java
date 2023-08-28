package com.example.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.domain.HandleSignalPort;
import com.example.domain.exception.AlgoInvocationException;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.util.ResourceUtils;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class IntegerToAlgoOutputTest {

  @Autowired
  private HandleSignalPort signalServiceAdapter;

  @Test
  public void test_1(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/1.txt");
    var expected = Files.readString(file.toPath());
    assertLogOutput(expected, actual);
  }

  @Test
  public void test_2(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(2);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/2.txt");
    var expected = Files.readString(file.toPath());
    assertLogOutput(expected, actual);
  }

  @Test
  public void test_3(CapturedOutput co) throws IOException, AlgoInvocationException {
    signalServiceAdapter.handleSignal(3);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/3.txt");
    var expected = Files.readString(file.toPath());
    assertLogOutput(expected, actual);
  }

  @Test
  public void test_default(CapturedOutput co) throws IOException, AlgoInvocationException {
    // assume we don't support -1 integer
    signalServiceAdapter.handleSignal(-1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/default.txt");
    var expected = Files.readString(file.toPath());
    assertLogOutput(expected, actual);
  }

  // support different line endings platforms
  private void assertLogOutput(String expected, String actual) {
    var escapedExpected = StringUtils.normalizeSpace(expected);
    var escapedActual = StringUtils.normalizeSpace(actual);
    assertTrue(escapedActual.contains(escapedExpected));
  }
}
