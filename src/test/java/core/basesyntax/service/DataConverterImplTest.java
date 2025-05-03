package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
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
    void convertToTransaction_validData_ok() {
        List<String> input = List.of("b,apple,20", "s,banana,10");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        assertEquals(2, transactions.size());

        assertEquals(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20), transactions.get(0));
        assertEquals(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 10), transactions.get(1));
    }

    @Test
    void convertToTransaction_invalidFormat_notOk() {
        List<String> input = List.of("invalid,line");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(input));
        assertFalse(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> input = List.of("b,apple,abc");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(input));
        assertTrue(exception.getMessage().contains("Invalid quantity"));
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> input = List.of("x,apple,20");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(input));
        assertEquals("Error processing line: x,apple,20", exception.getMessage());
    }

    @Test
    void convertToTransaction_emptyInput_ok() {
        List<String> input = List.of();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);
        assertTrue(transactions.isEmpty());
    }
}
