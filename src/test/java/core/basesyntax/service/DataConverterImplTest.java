package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,50"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "apple", 50)
        );
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertEquals(expected, result);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).hashCode(), result.get(i).hashCode());
        }
    }

    @Test
    void convertToTransaction_emptyData_returnsEmptyList() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_ignoresEmptyLines_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "   ",
                "s,apple,30"
        );
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertEquals(2, result.size());
    }

    @Test
    void convertToTransaction_invalidPartsCount_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "banana,20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_missingType_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                ",banana,20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_invalidQuantityFormat_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_negativeQuantity_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,-5"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_unknownOperation_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,10"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(input));
    }
}
