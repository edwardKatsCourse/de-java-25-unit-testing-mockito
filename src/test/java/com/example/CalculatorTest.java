package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    // should <do something>
    // when <something called/happened>

    @Test
    @DisplayName("should return sum a + b, when .sum() called")
    void shouldReturnSumVariablesWhenSumCalled() {
        double expectedResult = 20;

        double a = 16;
        double b = 4;

        double result = new Calculator().sum(a, b);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("should return divide a/b, when b != 0")
    void shouldReturnDivideResultWhenBIsNotZero() {
        double expectedResult = 4;

        double a = 16;
        double b = 4;

        double result = new Calculator().divide(a, b);

        // expected 4 == 4 actual
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException, when b == 0")
    void shouldThrowIllegalArgumentExceptionWhenBIsZero() {
        double a = 16;
        double b = 0;

        String expectedErrorMessage = "Cannot divide by zero";

        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Calculator().divide(a, b);
        });

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedErrorMessage, result.getMessage());
    }
}
