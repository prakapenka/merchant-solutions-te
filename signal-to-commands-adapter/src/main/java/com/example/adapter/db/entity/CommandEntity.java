package com.example.adapter.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.domain.AlgoOperations;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "command")
public class CommandEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  Long id;

  Integer ordinal;

  @ManyToOne
  SignalEntity signal;

  @Enumerated(EnumType.STRING)
  AlgoOperations operation;

  // random json
  private String payload;
}
