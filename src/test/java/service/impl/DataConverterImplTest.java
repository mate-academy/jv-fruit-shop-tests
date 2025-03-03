package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ok() {
        List<String> report = Arrays.asList(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(report);

        assertEquals(2, transactions.size(), "List should contain 2 transactions");
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(10, transactions.get(1).getQuantity());
    }

    @Test
    void convertToTransactionEmptyList_notOk() {
        List<FruitTransaction> transactions = dataConverter
                .convertToTransaction(List.of("operation,fruit,quantity"));
        assertTrue(transactions.isEmpty(), "List should be empty when input contains only header");
    }
}
