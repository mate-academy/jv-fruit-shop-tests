package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convert_linesNull_notOK() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convert(null);
        });
    }

    @Test
    void convert_validData_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,10"
        );
        List<FruitTransaction> actualTransactions = dataConverter.convert(lines);
        assertEquals(1, actualTransactions.size());
        FruitTransaction transaction = actualTransactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void convert_negativeQuantity_notOk() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,-10"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convert(lines);
        });
    }

    @Test
    void convert_emptyLines_notOk() {
        List<String> lines = List.of();
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convert(lines);
        });
    }

    @Test
    void convert_unknownOperation_notOk() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "x,banana,10"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convert(lines);
        });
    }

    @Test
    void convert_invalidQuantityFormat_notOk() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convert(lines);
        });
    }
}
