package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_dataValid_ok() {
        List<String> fruits = new ArrayList<>();
        fruits.add("operation,fruit,quantity");
        fruits.add("b,apple,20");
        fruits.add("b,banana,100");

        List<FruitTransaction> actualTransactions = dataConverter.convertToTransaction(fruits);
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));

        assertIterableEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void convertToTransaction_emptyList_ok() {
        List<String> emptyList = new ArrayList<>();

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(emptyList);
        assertEquals(0, transactions.size());
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> unknownType = List.of("type,fruit,quantity", "y,banana,100");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(unknownType));
        assertEquals("Unknown operation type! y", exception.getMessage());
    }
}
