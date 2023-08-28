package com.example.adapter.db;

import com.example.adapter.db.entity.CommandEntity;
import com.example.adapter.db.entity.SignalEntity;
import com.example.adapter.db.mapper.EntityToCommandMapper;
import com.example.domain.Command;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBCommandsAdapter {

  private final CommandRepository commandRepository;
  private final SignalRepository signalRepository;

  private final EntityToCommandMapper mapper;

  public List<Command> getCommandsForId(long id) {
    return signalRepository.findById(id)
        // Long.MIN_VALUE is equivalent of default
        .or(() -> signalRepository.findById(Long.MIN_VALUE))
        .map(SignalEntity::getCommands)
        .map(this::getSortedList)
        .orElse(Collections.emptyList());
  }

  @Transactional
  public void addSignalAndCommands(int signal, List<Command> commands) {
    SignalEntity se = new SignalEntity();
    se.setId((long) signal);
    List<CommandEntity> ceList = getCommandEntitiesFromCommands(commands);
    se.setCommands(ceList);
    signalRepository.save(se);
  }

  private List<CommandEntity> getCommandEntitiesFromCommands(List<Command> commands) {
    var returnList = new ArrayList<CommandEntity>(commands.size());
    for (int i = 0; i < commands.size(); i++) {
      var commandEntity = mapper.mapFromCommand(commands.get(i));
      commandEntity.setOrdinal(i);
      returnList.add(commandEntity);
    }
    return returnList;
  }

  private List<Command> getSortedList(List<CommandEntity> entityList) {

    List<CommandEntity> toSort = new ArrayList<>(entityList);
    toSort.sort(Comparator.comparingInt(CommandEntity::getOrdinal));

    List<Command> collect = new ArrayList<>();
    for (CommandEntity commandEntity : toSort) {
      Command command = mapper.fromCommandEntity(commandEntity);
      collect.add(command);
    }
    return collect;
  }

}
