package com.example.adapter.db.mapper;

import com.example.adapter.db.entity.CommandEntity;
import com.example.domain.AlgoOperations;
import com.example.domain.Command;
import com.example.domain.TwoIntegersCommand;
import com.example.domain.VoidCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityToCommandMapper {

  private final ObjectMapper om;

  public CommandEntity mapFromCommand(Command command) {
    var newEntity = new CommandEntity();
    newEntity.setOperation(command.getOperation());
    if (command instanceof TwoIntegersCommand twi) {
      try {
        newEntity.setPayload(
            om.writeValueAsString(new TwoIntegerContainer(twi.getA(), twi.getB())));
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Unable to serialize payload for operation: "
            + command.getOperation(), e);
      }
    }
    return newEntity;
  }

  public Command fromCommandEntity(CommandEntity ce) {
    var operation = ce.getOperation();
    if (operation == AlgoOperations.SET_ALGO_PARAM) {
      try {
        var container = om.readValue(ce.getPayload(), TwoIntegerContainer.class);
        return new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM,
            container.first(), container.second());
      } catch (JsonProcessingException e) {
        throw new RuntimeException("unable to deserialize command payload: " + operation, e);
      }
    } else {
      return new VoidCommand(operation);
    }
  }
}
