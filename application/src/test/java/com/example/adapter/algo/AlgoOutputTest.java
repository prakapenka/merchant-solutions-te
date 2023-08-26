package com.example.adapter.algo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.util.ResourceUtils;

@ExtendWith(OutputCaptureExtension.class)
class AlgoOutputTest {

  private SignalServiceAdapter signalServiceAdapter;

  @BeforeEach
  void beforeEach() {
    this.signalServiceAdapter = new SignalServiceAdapter();
    signalServiceAdapter.init();
  }

  @Test
  public void test_1(CapturedOutput co) throws IOException {
    signalServiceAdapter.handleSignal(1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/1.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_2(CapturedOutput co) throws IOException {
    signalServiceAdapter.handleSignal(2);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/2.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_3(CapturedOutput co) throws IOException {
    signalServiceAdapter.handleSignal(3);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/3.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }

  @Test
  public void test_default(CapturedOutput co) throws IOException {
    // assume we don't support -1 integer
    signalServiceAdapter.handleSignal(-1);
    var actual = co.getOut();
    var file = ResourceUtils.getFile("classpath:algo/default.txt");
    var expected = Files.readString(file.toPath());
    assertEquals(expected, actual);
  }
}
