package com.example.adapter.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "signal")
public class SignalEntity {

  @Id
  Long id;

  @OneToMany(mappedBy = "signal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<CommandEntity> commands;
}
