package com.example.adapter.signal;

import com.example.adapter.db.DBCommandsAdapter;
import com.example.domain.Command;
import com.example.domain.GetSignalCommandList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignalToCommandAdapter implements GetSignalCommandList {

  private final DBCommandsAdapter adapter;

  @Override
  public List<Command> getCommandList(int i) {
    return adapter.getCommandsForId(i);
  }
}
