package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String HEADER = "operation,fruit,quantity";
    private static final String APPLE_PART = "b,apple,50";
    private static final String BANANA_PART = "s,banana,20";
    private static final String INVALID_PART = "b,banana";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 20;
    private static final int ACTUAL_SIZE = 2;
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = Arrays.asList(
                HEADER,
                APPLE_PART,
                BANANA_PART);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE,
                        APPLE_QUANTITY),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        BANANA,
                        BANANA_QUANTITY)
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(data);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_invalidData_notOk() {
        List<String> data = Arrays.asList(
                HEADER,
                APPLE_PART,
                BANANA_PART,
                INVALID_PART);
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertEquals(ACTUAL_SIZE, transactions.size());
    }
}
