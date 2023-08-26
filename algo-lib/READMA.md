# 3d party algo library

Algo team provides a library containing all the required Trading Algo software. The job can be
executed with a given ‘signal’.

In the following module the ‘SignalHandler’ interface and ‘Algo’ implementation classes belong to
the Trading Algo 3d party library.

Library code cannot be modified.

## Code listing as was given in task description:

Once again -
> [!IMPORTANT]  
> Library code cannot be modified.

So unfortunately let's assume we are dealing with code located without package, class Algo does not
have public visibility.

### Interface `SignalHandler`

```java
/**
 * This is an upcall from our trading system, and we cannot change it.
 */
interface SignalHandler {

  void handleSignal(int signal);
}
```

### Implementation `Algo`

```java
/**
 * This is implemented in a third-party library and we cannot change it
 */
class Algo {

  public void doAlgo() {
    System.out.println("doAlgo");
  }

  public void cancelTrades() {
    System.out.println("cancelTrades");
  }

  public void reverse() {
    System.out.println("reverse");
  }

  public void submitToMarket() {
    System.out.println("submitToMarket");
  }

  public void performCalc() {
    System.out.println("performCalc");
  }

  public void setUp() {
    System.out.println("setUp");
  }

  public void setAlgoParam(int param, int value) {
    System.out.println("setAlgoParam " + param + "," + value);
  }
}
```
