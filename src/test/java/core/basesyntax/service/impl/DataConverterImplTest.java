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
    void convertToTransaction_noData_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(List.of());
        });
    }

    @Test
    void convertToTransaction_lineIsLonger_NotOk() {
        List<String> stringList = List.of("fruit,quantity", "b,apple,123,r");
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(stringList);
        });
    }

    @Test
    void convertToTransaction_Ok() {
        List<String> stringList = List.of("fruit,quantity", "b,apple,123");

        List<FruitTransaction> expect = List.of(new FruitTransaction(
                FruitTransaction.Operation.fromCode("b"), "apple", 123));

        assertEquals(expect, dataConverter.convertToTransaction(stringList));
    }
}
