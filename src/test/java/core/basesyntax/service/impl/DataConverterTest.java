package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void fruitList_validData_Ok() {
        List<String> validLIst = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(OperationType.BALANCE, "banana", 20),
                new FruitTransaction(OperationType.BALANCE, "apple", 100),
                new FruitTransaction(OperationType.SUPPLY, "banana", 100),
                new FruitTransaction(OperationType.PURCHASE, "banana", 13),
                new FruitTransaction(OperationType.RETURN, "apple", 10),
                new FruitTransaction(OperationType.PURCHASE, "apple", 20)
        );
        List<FruitTransaction> actual = dataConverter.fruitList(validLIst);
        assertEquals(expected, actual);
    }

    @Test
    void fruitList_InvalidDataFormat_NotOk() {
        List<String> invalidData = List.of("b,banana,20,15", "b,apple,100");
        assertThrows(RuntimeException.class, () -> dataConverter.fruitList(invalidData),
                "Invalid input data format");
    }

    @Test
    void fruitList_NegativeFruitQuantity_NotOk() {
        List<String> invalidData = List.of("b,banana,20", "b,apple,-100");
        assertThrows(RuntimeException.class, () -> dataConverter.fruitList(invalidData),
                "Quantity of fruits can't be negative");
    }
}
