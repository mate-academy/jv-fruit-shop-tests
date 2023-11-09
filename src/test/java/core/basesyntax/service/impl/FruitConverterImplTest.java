package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitConverter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitConverterImplTest {
    private FruitConverter fruitConverter;

    @BeforeEach
    void setUp() {
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
        assertEquals(3, result.size());
        assertEquals(Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convertData_emptyDataList_ok() {
        List<String> testData = Collections.emptyList();
        List<FruitTransaction> result = fruitConverter.convertData(testData);
        assertTrue(result.isEmpty());
    }
}
