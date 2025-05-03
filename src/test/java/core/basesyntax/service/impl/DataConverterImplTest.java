package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final List<String> EMPTY_LIST = List.of();
    private static final List<String> NORMAL_LIST = List.of(
            "type,fruit,quantity", "b,banana,20", "b,apple,100");
    private static final List<FruitTransaction> EXPECTED_LIST_AFTER_METHOD = List.of(
            FruitTransaction.of(FruitTransaction.Operation.BALANCE, "banana", 20),
            FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 100));
    private static final List<String> LIST_WITH_INVALID_LENGTH_OF_STRING = List.of(
            "b,apple,100", "b,banana"
    );
    private static final List<String> LIST_WITH_INVALID_OPERATION_TYPE = List.of(
            "b,apple,100", "h,banana"
    );
    private static DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_EmptyList_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransactions(EMPTY_LIST);
        });
    }

    @Test
    void convertToTransaction_NormalValues_Ok() {
        List<FruitTransaction> actual = dataConverter.convertToTransactions(NORMAL_LIST);
        assertEquals(EXPECTED_LIST_AFTER_METHOD, actual);
    }

    @Test
    void convertToTransactions_StringLength2_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransactions(LIST_WITH_INVALID_LENGTH_OF_STRING);
        });
    }

    @Test
    void convertToTransactions_WithInvalidOperationType_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransactions(LIST_WITH_INVALID_OPERATION_TYPE);
        });
    }
}
