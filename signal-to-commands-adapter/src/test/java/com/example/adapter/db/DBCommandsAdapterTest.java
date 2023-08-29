package com.example.adapter.db;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.adapter.AllTestConfiguration;
import com.example.domain.AlgoOperations;
import com.example.domain.StringCommand;
import com.example.domain.TwoIntegersCommand;
import com.example.domain.VoidCommand;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@ContextConfiguration(classes = AllTestConfiguration.class)
public class DBCommandsAdapterTest {

  @Autowired
  private DBCommandsAdapter adapter;

  @Test
  void testCanQueryDatabase() {
    var commandList = adapter.getCommandsForId(Long.MIN_VALUE);
    assertNotNull(commandList);
    assertEquals(2, commandList.size());

    assertAll(
        () -> assertEquals(AlgoOperations.CANCEL_THREADS, commandList.get(0).getOperation()),
        () -> assertEquals(AlgoOperations.DO_ALGO, commandList.get(1).getOperation())
    );
  }

  @Test
  void testCanAddSignalAndCommands() {
    adapter.addSignalAndCommands(13, List.of(
        new VoidCommand(AlgoOperations.SETUP),
        new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 2)
    ));

    var commandList = adapter.getCommandsForId(13);
    assertNotNull(commandList);
    assertEquals(2, commandList.size());
  }

  @Test
  void testCanSupportMagic() {
    adapter.addSignalAndCommands(113, List.of(
        new StringCommand(AlgoOperations.MAGIC, "spell-113")
    ));

    var commandList = adapter.getCommandsForId(113);
    assertNotNull(commandList);
    assertEquals(1, commandList.size());

    final var actualCommand = (StringCommand) commandList.get(0);
    assertAll(
        () -> assertEquals(AlgoOperations.MAGIC, actualCommand.getOperation()),
        () -> assertEquals("spell-113", actualCommand.getSpell())
    );
  }
}
