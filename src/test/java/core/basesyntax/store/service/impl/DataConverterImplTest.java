package core.basesyntax.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.DataConverter;
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
    void convertToTransaction_shouldConvertValidDataCorrectly() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,orange,30"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);

        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30)
        );
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void convertToTransaction_shouldHandleIncorrectFormatGracefully() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "INVALID,banana,50"
        );

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });

        assertEquals("Invalid operation code: INVALID", thrown.getMessage());
    }

    @Test
    void convertToTransaction_shouldHandleIncompleteData() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "s,banana"
        );

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });

        assertEquals("Insufficient data for transaction: s,banana", thrown.getMessage());
    }
}
