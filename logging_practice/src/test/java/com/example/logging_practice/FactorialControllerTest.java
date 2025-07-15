package com.example.logging_practice;

import com.example.logging_practice.controller.FactorialController;
import com.example.logging_practice.exception.GlobalExceptionHandler;
import com.example.logging_practice.service.FactorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FactorialControllerTest {
    private MockMvc mockMvc;
    private FactorialService mockService;

    @BeforeEach
    void setup() {
        mockService = Mockito.mock(FactorialService.class);
        FactorialController controller = new FactorialController(mockService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testValidInput() throws Exception {
        when(mockService.factorial("5")).thenReturn(BigInteger.valueOf(120));

        mockMvc.perform(get("/factorial/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("120"));
    }

    @Test
    void testValidBigInput() throws Exception {
        String input = "100";
        String expected = "93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000";

        when(mockService.factorial(input)).thenReturn(new BigInteger(expected));

        mockMvc.perform(get("/factorial/" + input))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    void testNegativeInput() throws Exception {
        when(mockService.factorial("-3")).thenThrow(new IllegalArgumentException("Negative input: -3"));

        mockMvc.perform(get("/factorial/-3"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Negative input: -3"));
    }

    @Test
    void testNonNumericInput() throws Exception {
        when(mockService.factorial("abc")).thenThrow(new IllegalArgumentException("Invalid input: not a number"));

        mockMvc.perform(get("/factorial/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid input: not a number"));
    }
}
