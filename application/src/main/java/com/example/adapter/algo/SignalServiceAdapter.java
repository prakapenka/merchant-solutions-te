package com.example.adapter.algo;

import com.example.domain.exception.AlgoInvocationException;
import com.example.service.port.HandleSignalPort;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Method;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

@Component
public class SignalServiceAdapter implements HandleSignalPort {

  private Object instance;
  private Method methodHandler;

  @PostConstruct
  void init() {
    try {
      var klass = Class.forName("Application");
      var constructor = klass.getDeclaredConstructor();
      constructor.setAccessible(true);
      instance = constructor.newInstance();

      Method methodHandler = klass.getMethod("handleSignal", int.class);
      methodHandler.setAccessible(true);
      this.methodHandler = methodHandler;
    } catch (Exception any) {
      throw new ApplicationContextException("Unable to load 'Application' from default package.",
          any);
    }
  }

  @Override
  public void handleSignal(int signal) {
    try {
      methodHandler.invoke(instance, signal);
    } catch (Exception e) {
      throw new AlgoInvocationException("Unable to invoke algorithm", e);
    }
  }
}
