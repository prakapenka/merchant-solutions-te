package com.example.domain;

import lombok.Getter;

@Getter
public abstract class Command {

  private final AlgoOperations operation;

  protected Command(AlgoOperations operation) {
    this.operation = operation;
  }
}
