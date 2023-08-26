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