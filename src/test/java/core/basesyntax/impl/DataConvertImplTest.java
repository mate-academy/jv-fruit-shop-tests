package core.basesyntax.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void countOfComponents_NotOk() {
        List<String> invalidLinesLength = List.of(
                "b,apple,22,orange",
                "p,apple,44,55",
                "r,apple,44,456"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(invalidLinesLength));

        assertTrue(exception.getMessage().contains("Invalid line format"),
                "Expected 'Invalid line format' but got: " + exception.getMessage());
    }

    @Test
    void thirdElement_NotOk() {
        List<String> invalidThirdComponent = List.of(
                "b,apple,-22",
                "b,apple,hello",
                "b,apple,-55"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(invalidThirdComponent));

        assertTrue(exception.getMessage().contains("Invalid quantity format")
                || exception.getMessage().contains("Quantity cannot be negative"),
                "Expected 'Invalid quantity format' or "
                        + "'Quantity cannot be negative' but got: "
                        + exception.getMessage());
    }

    @Test
    void returnTransactionNotNull_Ok() {
        List<String> validInputLines = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(validInputLines);
        assertNotNull(transactions);
    }
}
