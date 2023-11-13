package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitConverter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitConverterImplTest {
    private static final int LENGTH_OF_LINE = 3;
    private static final int FIRST_LINE = 0;
    private static final String BANANA = "banana";

    private static FruitConverter fruitConverter;

    @BeforeAll
    static void beforeAll() {
        fruitConverter = new FruitConverterImpl();
    }

    @Test
    void convertData_conversion_ok() {
        List<String> testData = List.of(
                "b,banana,20",
                "s,apple,100",
                "p,banana,13"
        );
        List<FruitTransaction> result = fruitConverter.convertData(testData);
        assertEquals(LENGTH_OF_LINE, result.size());
        FruitTransaction firstTransaction = result.get(FIRST_LINE);
        assertEquals(Operation.BALANCE, firstTransaction.getOperation());
        assertEquals(BANANA, firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());
    }

    @Test
    void convertData_emptyDataList_ok() {
        List<String> testData = Collections.emptyList();
        List<FruitTransaction> result = fruitConverter.convertData(testData);
        assertTrue(result.isEmpty());
    }
}
