package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static final String HEAD_FILE = "operation,fruit,quantity";
    private static final String INVALID_BALANCE_STRING = "b,apple";
    private static final int EXPECTED_LIST_SIZE = 1;
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String VALID_BALANCE_APPLE = "b,apple,10";
    private static final String VALID_SUPPLY_ORANGE = "s,orange,5";
    private static final int APPLE_QUANTITY = 10;
    private static final int ORANGE_QUANTITY = 5;
    private static ParserServiceImpl parser;
    private List<FruitTransaction> result;

    @BeforeAll
    public static void beforeClass() {
        parser = new ParserServiceImpl();
    }

    @AfterEach
    public void clearResultList() {
        result.clear();
    }

    @Test
    public void test_parse_with_null_input_ok() {
        result = parser.parse(null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void test_parse_with_empty_input_ok() {
        result = parser.parse(Collections.emptyList());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void test_parse_with_valid_input_ok() {
        List<String> input = Arrays.asList(HEAD_FILE, VALID_BALANCE_APPLE, VALID_SUPPLY_ORANGE);
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE,
                        APPLE_QUANTITY),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        ORANGE,
                        ORANGE_QUANTITY)
        );
        result = parser.parse(input);
        assertEquals(expected, result);
    }

    @Test
    public void test_parse_with_invalid_input_ok() {
        List<String> input = Arrays.asList(HEAD_FILE, INVALID_BALANCE_STRING, VALID_SUPPLY_ORANGE);
        result = parser.parse(input);
        assertEquals(EXPECTED_LIST_SIZE, result.size());
    }
}
