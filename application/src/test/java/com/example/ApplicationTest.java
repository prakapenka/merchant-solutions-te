package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class ApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @ParameterizedTest
  @ValueSource(ints = {
      1, 2, 3, 4, 5
  })
  public void testCanCallGetEndpoint(int code) throws Exception {
    mockMvc.perform(get("/" + code))
        .andExpect(status().isOk());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "null", "1e10", "not_an_integer"
  })
  public void testErrorGetEndpoint(String anyInput) throws Exception {
    mockMvc.perform(get("/" + anyInput))
        .andExpect(status().isBadRequest());
  }
}
