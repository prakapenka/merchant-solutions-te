package com.example.adapter.algo;

import com.example.domain.AlgoInstance;
import com.example.domain.AlgoInstanceFactory;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

@Component
public class ReflectionAlgoInstanceFactory implements AlgoInstanceFactory {

  private Class<?> klass;
  private Constructor<?> constructor;

  @PostConstruct
  void init() {
    try {
      this.klass = Class.forName("Algo");
      var constructor = this.klass.getDeclaredConstructor();
      constructor.setAccessible(true);
      this.constructor = constructor;
    } catch (ClassNotFoundException e) {
      // in case we cannot load class prefer to fail fast
      throw new ApplicationContextException("Unable to load 'Application' from default package.",
          e);
    } catch (NoSuchMethodException e) {
      // in case we cannot find constructor - will not be able to instantiate class
      // so we have to fail fast again.
      throw new ApplicationContextException(
          "Could not find default accessible constructor for Algo.",
          e);
    }
  }

  @Override
  public AlgoInstance getAlgoInstance() {
    try {
      var object = constructor.newInstance();
      return new ReflectionAlgoInstance(klass, object);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
