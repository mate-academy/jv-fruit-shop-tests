package core.basesyntax.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConvertImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConvertImplTest {
    private static DataConverter dataConverter;
    private static final String SEPARATOR = ",";

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConvertImpl();
    }

    @Test
    void countOfComponents_Ok() {
        List<String> invalidLines = List.of(
                "banana,20",
                "apple,30,50,200",
                "orange,40,"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            for (String line : invalidLines) {
                String[] arr = line.split(SEPARATOR);
                if (arr.length != 3) {
                    throw new IllegalArgumentException("Invalid line: " + line);
                }
            }
        });

    }

    @Test
    void returnTransactionNotNull_Ok() {
        List<String> invalidLines = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(invalidLines);
        assertNotNull(transactions);
    }
}
