package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();
    private List<String> inputReport;

    @BeforeEach
    void setUp() {
        inputReport = new ArrayList<>();
    }

    @Test
    void convertToTransaction_Ok() {
        inputReport.add("type,fruit,quantity");
        inputReport.add("b,banana,20");
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,
                "banana", 20);
        List<FruitTransaction> expected = List.of(fruitTransaction);
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertIterableEquals(expected, actual);
    }

    @Test
    void convertToTransaction_headersOnly_Ok() {
        inputReport.add("type,fruit,quantity");
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_emptyList_Ok() {
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual);
    }
}
