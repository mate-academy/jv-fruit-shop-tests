package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.InputDataType;
import model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InputDataResolver;

class InputDataResolverImplTest {
    private InputDataResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new InputDataResolverImpl();
    }

    @Test
    void dataResolverValidInput_ok() {
        List<String> input = Arrays.asList(
                "b,banana,100", "s,banana,25", "p,banana,80", "r,banana,10"
        );
        ArrayList<InputDataType> result = resolver.resolveData(input);
        int expectedElementsCount = 4;
        assertEquals(expectedElementsCount, result.size(),
                "Actual and expected sizes must be equals");

        assertEquals(Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit().getName());
        assertEquals(100, result.get(0).getFruit().getQuantity());

        assertEquals(Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("banana", result.get(1).getFruit().getName());
        assertEquals(25, result.get(1).getFruit().getQuantity());

        assertEquals(Operation.PURCHASE, result.get(2).getOperation());
        assertEquals("banana", result.get(2).getFruit().getName());
        assertEquals(80, result.get(2).getFruit().getQuantity());

        assertEquals(Operation.RETURN, result.get(3).getOperation());
        assertEquals("banana", result.get(3).getFruit().getName());
        assertEquals(10, result.get(3).getFruit().getQuantity());
    }

    @Test
    void dataResolverInvalidInput_notOk() {
        List<String> input = List.of("b,banana,", "b,apple,5", "b,orange,-8");

        assertThrows(RuntimeException.class, () -> resolver.resolveData(input),
                "Invalid input data " + input);
    }

    @Test
    void dataResolverEmptyInput_returnsEmptyList_ok() {
        List<String> input = new ArrayList<>();
        ArrayList<InputDataType> expected = new ArrayList<>();

        assertEquals(expected, resolver.resolveData(input),
                "Actual resolved data must be empty");
    }
}
