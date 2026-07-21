package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validLines_ok() {
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> actual = dataConverter.convert(inputLines);

        assertEquals(2, actual.size(),
                "Should parse exactly 2 transactions (ignoring header)");

        assertEquals(FruitTransaction.Operation.BALANCE,
                actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(20, actual.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY,
                actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(100, actual.get(1).getQuantity());
    }

    @Test
    void convert_emptyList_ok() {
        List<FruitTransaction> actual = dataConverter.convert(new ArrayList<>());
        assertTrue(actual.isEmpty(), "Result should be empty for empty input");
    }

    @Test
    void convert_invalidOperationCode_notOk() {
        List<String> invalidInput = List.of("x,banana,20");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convert(invalidInput);
        }, "Should throw an exception for unknown operation code");
    }
}
