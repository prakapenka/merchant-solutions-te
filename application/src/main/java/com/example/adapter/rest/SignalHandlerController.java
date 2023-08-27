package com.example.adapter.rest;


import com.example.domain.HandleSignalPort;
import com.example.domain.exception.AlgoInvocationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class SignalHandlerController {

  private final HandleSignalPort handleSignalPort;

  @GetMapping("/{signal}")
  public ResponseEntity<Void> handleSignal(@PathVariable @NotNull Integer signal)
      throws AlgoInvocationException {
    handleSignalPort.handleSignal(signal);
    return ResponseEntity.ok().build();
  }

}
