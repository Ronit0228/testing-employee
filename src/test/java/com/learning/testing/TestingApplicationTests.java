package com.learning.testing;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TestingApplicationTests {

    @BeforeAll
    static void beforeAllRun() {
        log.info("I will run before all methods");
    }

    @BeforeEach
    void setUp() {
        log.info("I will run everytime before every method");
    }

    @AfterAll
    static void afterAllRun() {
        log.info("I will run after all methods");
    }

    @AfterEach
    void tearDown() {
        log.info("Tearing down the method");
    }

    @Test
    @DisplayName("Test-One")
    void testOne() {
        log.info("Test one is running");
        int a = 6;
        int b = 6;

        int result = addTwoNumbers(a, b);
        log.info("Result is: {}", result);

//        Assertions.assertEquals(12, result);
//        log.info("Test one passed");

        Assertions.assertThat(result)
                .isEqualTo(12)
                .isCloseTo(13, Offset.offset(1));


        String name = "apple";
        Assertions.assertThat(name)
                .isEqualTo("apple")
                .startsWith("app")
                .endsWith("le")
                .hasSize(5);
    }

    @Test
    @DisplayName("Test-One")
    void testTwo() {
        log.info("Test two is running");
    }

    @Test
    void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException() {

        Assertions.assertThatThrownBy(() -> divideTwoNumbers(1, 0))
                .isInstanceOf(ArithmeticException.class);
    }

    int addTwoNumbers(int a, int b) {
        return a + b;
    }

    double divideTwoNumbers(int a, int b) {
        try{
            return a / b;
        } catch (ArithmeticException arithmeticException) {
            log.error("Arithmetic Exception occurred : {}", arithmeticException.getLocalizedMessage());
            throw new ArithmeticException(arithmeticException.getLocalizedMessage());
        }


    }

}
