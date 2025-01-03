package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void fileConversion_Ok() {
        List<String> data = List.of("b,banana,20",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10");
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);
        assertEquals(result.get(0).getOperation(), FruitTransaction.Operation.BALANCE);
        assertEquals(result.get(0).getFruit(), "banana");
        assertEquals(result.get(0).getQuantity(), 20);
        assertEquals(result.get(1).getOperation(), FruitTransaction.Operation.SUPPLY);
        assertEquals(result.get(1).getFruit(), "apple");
        assertEquals(result.get(1).getQuantity(), 100);
        assertEquals(result.get(2).getOperation(), FruitTransaction.Operation.PURCHASE);
        assertEquals(result.get(2).getFruit(), "banana");
        assertEquals(result.get(2).getQuantity(), 13);
        assertEquals(result.get(3).getOperation(), FruitTransaction.Operation.RETURN);
        assertEquals(result.get(3).getFruit(), "apple");
        assertEquals(result.get(3).getQuantity(), 10);
    }

    @Test
    void resultLength_Ok() {
        List<String> data = List.of("b,banana,20",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10",
                "b,orange,15");
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);
        assertEquals(result.size(), 5);
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
        List<String> data = List.of("b, banana , 20");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}
