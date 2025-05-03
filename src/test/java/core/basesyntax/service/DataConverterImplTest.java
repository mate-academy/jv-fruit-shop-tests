package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertData_ValidData_Ok() {
        List<String> data = List.of("b,banana,20",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);
        assertEquals(expected, result);
    }

    @Test
    void dataEqualsNullOk() {
        List<String> data = new ArrayList<>();
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);
        assertEquals(result.size(), 0);
    }

    @Test
    void lineLengthBigger_NotOk() {
        List<String> data = List.of("b,banana,20,100");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void lineLengthShorter_NotOk() {
        List<String> data = List.of("b,banana");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
        assertEquals("Invalid input format. Expected 3 elements separated by commas.",
                exception.getMessage());
    }

    @Test
    void invalidQuantityFormat_NotOk() {
        List<String> data = List.of("b,banana,a");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void negativeQuantity_NotOk() {
        List<String> data = List.of("b,banana,-20");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
        assertEquals("Quantity cannot be negative: -20", exception.getMessage());
    }

    @Test
    void invalidOperationCode_NotOk() {
        List<String> data = List.of("c,banana,a");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
        assertEquals("Invalid operation code: c", exception.getMessage());
    }

    @Test
    void whitespaceInData_NotOk() {
        List<String> data = List.of("b, banana , 100");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
        assertEquals("Data format is incorrect. Data shouldn't contain ' '",
                exception.getMessage());
    }
}
