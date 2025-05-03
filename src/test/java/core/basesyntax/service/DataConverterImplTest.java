package core.basesyntax.service;

import static core.basesyntax.service.TestConstants.DEFAULT_APPLE_STRING;
import static core.basesyntax.service.TestConstants.DEFAULT_APPLE_TRANSACTION;
import static core.basesyntax.service.TestConstants.DEFAULT_BANANA_STRING;
import static core.basesyntax.service.TestConstants.DEFAULT_BANANA_TRANSACTION;
import static core.basesyntax.service.TestConstants.DEFAULT_ORANGE_STRING;
import static core.basesyntax.service.TestConstants.DEFAULT_ORANGE_TRANSACTION;
import static core.basesyntax.service.TestConstants.DEFAULT_STRAWBERRY_STRING;
import static core.basesyntax.service.TestConstants.DEFAULT_STRAWBERRY_TRANSACTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final List<FruitTransaction> DEFAULT_FRUITS = new ArrayList<>(
            List.of(DEFAULT_APPLE_TRANSACTION, DEFAULT_BANANA_TRANSACTION,
                    DEFAULT_STRAWBERRY_TRANSACTION, DEFAULT_ORANGE_TRANSACTION));
    private static final List<String> DEFAULT_TEST_STRINGS = new ArrayList<>(List.of(
            "type,fruit,quantity", DEFAULT_APPLE_STRING, DEFAULT_BANANA_STRING,
            DEFAULT_STRAWBERRY_STRING, DEFAULT_ORANGE_STRING));
    private static DataConverter dataParser;
    private List<String> fruits;
    private List<FruitTransaction> expected;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataConverterImpl();
    }

    @BeforeEach
    void setUp() {
        fruits = DEFAULT_TEST_STRINGS;
        expected = DEFAULT_FRUITS;
    }

    @Test
    void parse_ValidInput_ok() {
        List<FruitTransaction> parsedInfo = dataParser.convertToTransaction(fruits);
        assertEquals(expected, parsedInfo);
    }

    @Test
    void parse_nullInput_notOk() {
        expected = new ArrayList<>();
        List<FruitTransaction> parsedInfo = dataParser.convertToTransaction(null);
        assertEquals(expected, parsedInfo);
    }

    @Test
    void parse_invalidInput_notOk() {
        fruits.set(1, "invalid,data");
        String expected = "Invalid fruit transaction line: [invalid, data]";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataParser.convertToTransaction(fruits));
        assertEquals(expected, exception.getMessage());
    }
}
