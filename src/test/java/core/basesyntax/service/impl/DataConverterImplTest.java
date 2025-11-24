package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String INVALID_LINE = "b,banana";
    private static final String VALID_LINE = "b,banana,20";
    private DataConverter dataConverterImpl;

    @BeforeEach
    void beforeEach() {
        dataConverterImpl = new DataConverterImpl();
    }

    @Test
    void convertToTransactions_reportsIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterImpl.convertToTransactions(null));
    }

    @Test
    void convertToTransactions_reportNotValid_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterImpl.convertToTransactions(List.of(INVALID_LINE)));
    }

    @Test
    void convertToTransactions_fruitTransactionCreated_ok() {
        String[] parts = VALID_LINE.split(",");
        FruitTransaction expectedTransaction = FruitTransaction.of(
                FruitTransaction.Operation.fromCode(parts[0]),
                parts[1],Integer.parseInt(parts[2]));
        List<FruitTransaction> actual =
                dataConverterImpl.convertToTransactions(List.of(VALID_LINE));
        assertEquals(List.of(expectedTransaction), actual);
    }
}
