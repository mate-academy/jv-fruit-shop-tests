package core.basesyntax.store.util.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.store.model.FruitTransaction;
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
                "operation,fruit,quantity", // заголовок
                "b,apple,100", // для BALANCE
                "s,banana,50", // для SUPPLY
                "p,orange,30" // для PURCHASE
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);

        assertEquals(3, transactions.size(), "The number of transactions should be 3.");

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(50, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("orange", transactions.get(2).getFruit());
        assertEquals(30, transactions.get(2).getQuantity());
    }

    @Test
    void convertToTransaction_shouldHandleIncorrectFormatGracefully() {
        List<String> lines = List.of(
                "operation,fruit,quantity", // заголовок
                "b,apple,100", // коректний рядок
                "INVALID,banana,50" // некоректний код операції
        );

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });

        assertEquals("Invalid operation code: INVALID", thrown.getMessage());
    }

    @Test
    void convertToTransaction_shouldHandleIncompleteData() {
        List<String> lines = List.of(
                "operation,fruit,quantity", // заголовок
                "b,apple,100", // коректний рядок
                "s,banana" // неповний рядок (відсутня кількість)
        );

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });

        assertEquals("Insufficient data for transaction: s,banana", thrown.getMessage());
    }
}
