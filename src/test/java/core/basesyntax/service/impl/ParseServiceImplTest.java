package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static final String HEAD_FILE = "operation,fruit,quantity";
    private static final String INVALID_BALANCE_STRING = "b,apple";
    private static final int EXPECTED_LIST_SIZE = 1;
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String VALID_BALANCE_APPLE = "b,apple,10";
    private static final String VALID_SUPPLY_ORANGE = "s,orange,5";
    private static final int APPLE_QUANTITY = 10;
    private static final int ORANGE_QUANTITY = 5;
    private static ParseServiceImpl parser;
    private List<FruitTransaction> result;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParseServiceImpl();
    }

    @After
    public void clearResultList() {
        result.clear();
    }

    @Test
    public void test_Parse_With_Null_Input_Ok() {
        result = parser.parse(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void test_Parse_With_Empty_Input_Ok() {
        result = parser.parse(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void test_Parse_With_Valid_Input_Ok() {
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
    public void testParseWithInvalidInputOk() {
        List<String> input = Arrays.asList(HEAD_FILE, INVALID_BALANCE_STRING, VALID_SUPPLY_ORANGE);
        result = parser.parse(input);
        assertEquals(EXPECTED_LIST_SIZE, result.size());
    }
}
