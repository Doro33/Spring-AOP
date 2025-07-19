package com.example.fibonacci;

import com.example.fibonacci.controller.FibonacciController;
import com.example.fibonacci.exception.GlobalExceptionHandler;
import com.example.fibonacci.service.FibonacciService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class FibonacciApplicationTests {
    private MockMvc mockMvc;
    private FibonacciService mockService;

    @BeforeEach
    void setup() {
        mockService = Mockito.mock(FibonacciService.class);
        FibonacciController controller = new FibonacciController(mockService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testValidInput() throws Exception {
        when(mockService.fibonacci(10)).thenReturn(BigInteger.valueOf(55));

        mockMvc.perform(get("/fibonacci/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("55"));
    }

    @Test
    void testValidBigInput() throws Exception {
        int input = 100;
        String expected = "354224848179261915075";

        when(mockService.fibonacci(input)).thenReturn(new BigInteger(expected));

        mockMvc.perform(get("/fibonacci/" + input))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    void testNegativeInput() throws Exception {
        when(mockService.fibonacci(-3)).thenThrow(new IllegalArgumentException("Index cannot be negative"));

        mockMvc.perform(get("/fibonacci/-3"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Index cannot be negative"));
    }

    @Test
    void testNonNumericInput() throws Exception {
        mockMvc.perform(get("/fibonacci/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Input must be a valid integer."));
    }
}
