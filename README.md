# merchant-solutions-te

Example of application that handles http requests and working with existed code.

## Requirements

Your team has been asked to write a new Trading application.
You work with an Algo team who provide you a [library](./algo-lib/READMA.md) containing all the
required Trading Algo software and the job of your platform is to execute the Algo when given a
‘signal’.
In the following section the ‘SignalHandler’ and ‘Algo’ classes belong to
the [Trading Algo library](./algo-lib/READMA.md) and cannot be modified.

Your team has written the ‘TradingApplication’ code to be able to process one of these signals (a
simple integer). Each signal specification is given to your team in the form of a JIRA from your
analysts.
The role of your team is to implement the new signal specifications and release to production as
quickly as possible.
While the current ‘TradingApplication’ code only has three signals, once in production it is
expected that up to 50 new signals will be added per month (600 after year one, 1200 after year two
etc).

## The Task

We would like you to make any changes to your teams code to make the code:

- Easier for your team to understand and debug
- Easier for your team to maintain and add new signals
- Easier to test
- The code should have appropriate levels of testing to ensure that the stated
  requirements are met.

The code should be a running service with a single http endpoint for receiving the `signal` which
will then be passed through the [SignalHandler](./algo-lib/src/main/java/SignalHandler.java)
interface and onto your application.

## Solution

### Phase 0.

Based on initial project structure, let's try to embed initial solution into a http web server, make
sure that we could recieve signal and pass it to existed implementation without modifications.
Here on this phase we will create rest web server that accepts integer and passes this integer into
existed SignalHandlerService.

At the end of phase 0, we will have rest service that accepts integer and invoke some algo
functionality.

Algo library provides code in default package. This is bad style, in java it is not possible to
import classes from default package (default namespace).
To solve that, we will use reflection to load classes and invoke methods indirectly.

At the end of phase 0 we have http (spring boot) server that wraps initial Application
implementation as it is and exposes it with GET endpoint.
Also at phase 0 we create possible tests for algo invocations as far as simple tests for rest
endpoint.

### Phase 1

On this phase we will refactor original `Application` class that is used to call `Algo`
functionally.
Let's look how that code was implemented originally.
For example, for `integer = 1` we have next sequence of calls:

```java
public void handleSignal(int signal){
        Algo algo=new Algo();
        switch(signal){
        case 1:
        algo.setUp();
        algo.setAlgoParam(1,60);
        algo.performCalc();
        algo.submitToMarket();
        break;
        ...
        algo.doAlgo();
```

i.e. for every input signal we:

- create new instance of `Algo` object
- then depending on integer value we call different sequence of methods
- at the end calling `doAlgo()`

We could imagine that for Algo we have next list of operations:

- `doAlgo()`
- `cancelTrades()`
- `reverse()`
- `submitToMarket()`
- `performCalc()`
- `setUp()`
- `setAlgoParam(int param, int value)` which by itself takes 2 input parameters.

...we assume we have Algo object more or less stable and we have finite amount of operations we have
to perform on that object.
Most of method call are of type void, while some accepts some input (2 integers)
All methods does not have return type and every time we execute Algo we do create new instance of
Algo.

We will introduce new domain layer, where we do create abstract commands. We expect that for new
integers we could have different command sequences.
So that if we do not introduce new methods for Algo class and do not modifying existed method
signatures, we could operates with abstract commands.
In case Algo object will be modified, we also will have to modify our mappings layer.

At the end of this stage we will have separate module to provide abstract domain commands, one
separate module that
provides implementation that bounds abstract commands to real Algo calls, and will add this module
as dependency.

We still have hardcoded command sequences for supported integers, which we will solve on nex stage.

### Phase 2

At this phase we still have hardcoded commands per integer, like it is defined in class

```java

@Service
@RequiredArgsConstructor
public class IntegerToAlgoService implements HandleSignalPort {
  ...

  List<Command> getCommandList(int i) {
    return switch (i) {
      case 1 -> List.of(
              new VoidCommand(AlgoOperations.SETUP),
              new TwoIntegersCommand(AlgoOperations.SET_ALGO_PARAM, 1, 60),
              new VoidCommand(AlgoOperations.PERFORM_CALC),
              new VoidCommand(AlgoOperations.SUBMIT_TO_MARKET),
              new VoidCommand(AlgoOperations.DO_ALGO)
      );
  ...
```

At this stage we will refactor it and create a persistence layer with information about which
commands corresponds to which integer values.

We will create new module [signal-to-commands-adapter](./signal-to-commands-adapter/README.md) that
would create sql schema and would provide functionality to store commands types.

Commands payload could be generic and more complex, then initially 2 integers. So decision was made
serialize command payload as json and store in database as a `VARCHAR`.

Please check database
schema [here](./signal-to-commands-adapter/src/main/resources/db/changelog/changes-0.sql).
Please check details on how commands payload
serialized/deserialized [here](./signal-to-commands-adapter/src/main/java/com/example/adapter/db/mapper/EntityToCommandMapper.java)

On this phase we have solved problem to add new commands (assuming main `Algo` class will not be
changed).
We could add/change associated commands with every integer signal. Pay attention, to
simulate `default` behaviour, when no assosiated signal found, we use `Long.MIN_VALUE as a default
signal mapping. For the details please refer
this [sql script](./signal-to-commands-adapter/src/main/resources/db/changelog/changes-1.sql) and
this [adapter implementation](./signal-to-commands-adapter/src/main/java/com/example/adapter/db/DBCommandsAdapter.java).