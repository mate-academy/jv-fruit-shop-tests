package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
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
    public void convertToTransaction_emptyData() {
        List<String> emptyData = Collections.emptyList();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(emptyData);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void convertToTransaction_nullData() {
        List<String> nullData = null;
        assertThrows(NullPointerException.class,
                () -> dataConverter.convertToTransaction(nullData));
    }

    @Test
    public void convertToTransaction_invalidData() {
        // Arrange
        DataConverterImpl dataConverter = new DataConverterImpl();
        List<String> data = Arrays.asList(
                "INVALID,Apple,10",
                "PURCHASE,Banana,abc",
                "RETURN,Orange"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}
