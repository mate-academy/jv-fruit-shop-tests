package fruitshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.model.FruitTransaction;
import fruitshop.service.DataConverter;
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
    void convertToTransactions_validLines_ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransactions(lines);

        assertEquals(2, transactions.size());

        FruitTransaction first = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals("banana", first.getFruit());
        assertEquals(20, first.getQuantity());

        FruitTransaction second = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        assertEquals("apple", second.getFruit());
        assertEquals(10, second.getQuantity());
    }

    @Test
    void convertToTransactions_onlyHeader_ok() {
        List<String> lines = List.of("type,fruit,quantity");

        List<FruitTransaction> transactions = dataConverter.convertToTransactions(lines);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransactions_invalidLine_notOk() {
        List<String> lines = List.of("type,fruit,quantity", "invalid,line");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransactions(lines)
        );

        assertEquals("Invalid line: invalid,line", exception.getMessage());
    }

    @Test
    void convertToTransactions_invalidOperationCode_notOk() {
        List<String> lines = List.of("type,fruit,quantity", "x,banana,5");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(lines)
        );

        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void convertToTransactions_invalidQuantity_notOk() {
        List<String> lines = List.of("type,fruit,quantity", "b,banana,abc");
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransactions(lines)
        );
        assertEquals("Invalid line: b,banana,abc", exception.getMessage());
    }

    @Test
    void convertToTransactions_emptyList_ok() {
        List<FruitTransaction> transactions = dataConverter.convertToTransactions(List.of());
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransactions_nullInput_notOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransactions(null)
        );
        assertEquals("Wrong input - it's nothing to convert", exception.getMessage());
    }
}
