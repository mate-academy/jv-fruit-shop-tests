package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
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
    void convertNotValidReport_NotOk() {
        List<String> actual = List.of("fruit,quantity", "s,banana");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(actual);
        });
    }

    @Test
    void convertTo_Ok() {
        List<String> actual = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100)
        );
        assertEquals(expected, dataConverter.convertToTransaction(actual));
    }

    @Test
    void convertNegativeQuantity_NotOk() {
        List<String> actual = List.of("type,fruit,quantity", "s,banana,-50");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(actual);
        });
    }
}
