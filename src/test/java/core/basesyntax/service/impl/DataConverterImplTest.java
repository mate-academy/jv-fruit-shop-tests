package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverterImpl dataConverter;
    private List<String> inputReport;

    @BeforeEach
    void setUp() {
        inputReport = new ArrayList<>();
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ValidInput_Ok() {
        inputReport.add("type,fruit,quantity");
        inputReport.add("b,banana,20");
        inputReport.add("s,apple,10");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        assertEquals(2, transactions.size());

        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals("banana", firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals("apple", secondTransaction.getFruit());
        assertEquals(10, secondTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_InvalidFormat_NotOk() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");
        inputReport.add("b,banana");

        Exception exception = assertThrows(RuntimeException.class, ()
                -> dataConverter.convertToTransaction(inputReport));

        assertEquals("Incorrect inputReport format should be `b,banana,20` but was b,banana",
                exception.getMessage());
    }

    @Test
    void convertToTransaction_OnlyHeaderLine_Ok() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        assertTrue(transactions.isEmpty());
    }
}
