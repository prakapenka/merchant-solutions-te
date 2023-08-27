package com.example.domain;

import lombok.Getter;

@Getter
public class VoidCommand extends Command {

  public VoidCommand(AlgoOperations operation) {
    super(operation);
  }
}
