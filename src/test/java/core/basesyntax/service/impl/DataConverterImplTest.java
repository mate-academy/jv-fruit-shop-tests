package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransactionImpl;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ValidData_Success() {
        List<String> input = List.of("b,apple,10", "s,banana,5");
        List<FruitTransactionImpl> transactions = dataConverter.convertToTransaction(input);

        assertEquals(2, transactions.size());
        assertEquals(FruitTransactionImpl.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(FruitTransactionImpl.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
    }

    @Test
    void convertToTransaction_EmptyList_ReturnsEmptyList() {
        List<String> input = List.of("");
        List<FruitTransactionImpl> transactions = dataConverter.convertToTransaction(input);

        assertEquals(0, transactions.size());
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_NullOrEmptyLines_SkipsThem() {
        List<String> input = List.of("", "s,apple,5");
        List<FruitTransactionImpl> transactions = dataConverter.convertToTransaction(input);

        assertEquals(1, transactions.size());
        assertEquals(FruitTransactionImpl.Operation.SUPPLY, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(5, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_InvalidNumber_ThrowsException() {
        List<String> input = List.of("b,apple,ten", "s,banana,13");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_InvalidOperation_ThrowsException() {
        List<String> input = List.of("j,apple,11", "s,banana,14");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_WrongFormatLine_SkipsIt() {
        List<String> input = List.of("apple,11", "r,apple,1");
        List<FruitTransactionImpl> transactions = dataConverter.convertToTransaction(input);

        assertEquals(1, transactions.size());
        assertEquals(FruitTransactionImpl.Operation.RETURN, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(1, transactions.get(0).getQuantity());
    }
}
