package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validLines_returnsTransactions() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,10",
                "s,banana,5"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);

        assertEquals(2, transactions.size());

        FruitTransaction balanceTransaction = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, balanceTransaction.getOperation());
        assertEquals("apple", balanceTransaction.getFruit());
        assertEquals(10, balanceTransaction.getQuantity());

        FruitTransaction supplyTransaction = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, supplyTransaction.getOperation());
        assertEquals("banana", supplyTransaction.getFruit());
        assertEquals(5, supplyTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_ignoresHeaderLine() {
        List<String> lines = List.of("type,fruit,quantity");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_invalidOperation_throwsException() {
        List<String> lines = List.of("x,apple,10");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(lines)
        );

        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<String> lines = new ArrayList<>();

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);

        assertTrue(transactions.isEmpty());
    }
}
