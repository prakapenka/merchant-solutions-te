package com.example.domain;

import lombok.Getter;

@Getter
public class TwoIntegersCommand extends Command {

  private final int a;
  private final int b;

  public TwoIntegersCommand(AlgoOperations operation, int a, int b) {
    super(operation);
    this.a = a;
    this.b = b;
  }
}
