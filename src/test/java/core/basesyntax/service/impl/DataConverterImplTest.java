package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validReport_ok() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);

        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(
                        FruitTransaction.Operation.BALANCE,
                        actual.get(0).getOperation()
                ),
                () -> assertEquals(
                        "banana",
                        actual.get(0).getFruit()
                ),
                () -> assertEquals(
                        20,
                        actual.get(0).getQuantity()
                ),
                () -> assertEquals(
                        FruitTransaction.Operation.SUPPLY,
                        actual.get(1).getOperation()
                ),
                () -> assertEquals(
                        "apple",
                        actual.get(1).getFruit()
                ),
                () -> assertEquals(
                        100,
                        actual.get(1).getQuantity()
                )
        );
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "s,banana,abc"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport)
        );

        assertEquals(
                "Invalid fruit quantity: abc",
                exception.getMessage()
        );
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "s,banana,-10"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport)
        );

        assertEquals(
                "Fruit quantity can't be negative: -10",
                exception.getMessage()
        );
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "x,banana,10"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport)
        );

        assertEquals(
                "Unknown operation code: x",
                exception.getMessage()
        );
    }

    @Test
    void convertToTransaction_headerOnly_returnsEmptyList() {
        List<String> inputReport = List.of(
                "type,fruit,quantity"
        );

        List<FruitTransaction> actual =
                dataConverter.convertToTransaction(inputReport);

        assertTrue(actual.isEmpty());
    }
}
