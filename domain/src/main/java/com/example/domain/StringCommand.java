package com.example.domain;

import lombok.Getter;

@Getter
public class StringCommand extends Command {

  final String spell;

  public StringCommand(AlgoOperations operation, String spell) {
    super(operation);
    this.spell = spell;
  }
}
