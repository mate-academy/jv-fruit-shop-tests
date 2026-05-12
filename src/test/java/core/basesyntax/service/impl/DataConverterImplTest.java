package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void convertValidLinesReturnsTransactions() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,15",
                "p,banana,7",
                "r,apple,3"
        );

        List<FruitTransaction> actual = dataConverter.convert(lines);

        assertEquals(4, actual.size());
        assertTransaction(actual.get(0), FruitTransaction.Operation.BALANCE, "banana", 20);
        assertTransaction(actual.get(1), FruitTransaction.Operation.SUPPLY, "apple", 15);
        assertTransaction(actual.get(2), FruitTransaction.Operation.PURCHASE, "banana", 7);
        assertTransaction(actual.get(3), FruitTransaction.Operation.RETURN, "apple", 3);
    }

    @Test
    void convertNullLinesThrowsException() {
        assertThrows(RuntimeException.class, () -> dataConverter.convert(null));
    }

    @Test
    void convertEmptyLinesThrowsException() {
        assertThrows(RuntimeException.class, () -> dataConverter.convert(List.of()));
    }

    @Test
    void convertInvalidLineThrowsException() {
        List<String> lines = List.of("type,fruit,quantity", "b,banana");

        assertThrows(RuntimeException.class, () -> dataConverter.convert(lines));
    }

    @Test
    void convertUnknownOperationThrowsException() {
        List<String> lines = List.of("type,fruit,quantity", "x,banana,20");

        assertThrows(RuntimeException.class, () -> dataConverter.convert(lines));
    }

    private void assertTransaction(FruitTransaction actual, FruitTransaction.Operation operation,
            String fruit, int quantity) {
        assertEquals(operation, actual.getOperation());
        assertEquals(fruit, actual.getFruit());
        assertEquals(quantity, actual.getQuantity());
    }
}
