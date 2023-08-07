package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {
    private static final String HEAD_FILE = "operation,fruit,quantity";
    private static final String INVALID_BALANCE_STRING = "b,apple,5A";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String VALID_BALANCE_APPLE = "b,apple,10";
    private static final String VALID_SUPPLY_ORANGE = "s,orange,5";
    private static final int APPLE_QUANTITY = 10;
    private static final int ORANGE_QUANTITY = 5;

    @Test
    public void test_ParseWithNullInput_Ok() {
        ParseServiceImpl parseService = new ParseServiceImpl();
        List<FruitTransaction> resultFruit
                = parseService.parse(null);
        assertNotNull(resultFruit);
        assertTrue(resultFruit.isEmpty());
    }

    @Test
    public void test_ParseWithEmptyInput() {
        ParseServiceImpl parseService = new ParseServiceImpl();
        List<FruitTransaction> resultFruit
                = parseService.parse(Collections.emptyList());
        assertNotNull(resultFruit);
        assertTrue(resultFruit.isEmpty());
    }

    @Test
    public void test_ParseWithValidInput_Ok() {
        ParseServiceImpl parseService = new ParseServiceImpl();
        List<String> stringList = Arrays.asList(
                HEAD_FILE, VALID_BALANCE_APPLE, VALID_SUPPLY_ORANGE);
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE, APPLE_QUANTITY),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        ORANGE, ORANGE_QUANTITY));
        List<FruitTransaction> resultFruit = parseService.parse(stringList);
        assertEquals(expected, resultFruit);
    }

    @Test
    public void test_ParseInvalidInput_notOk() {
        ParseServiceImpl parseService = new ParseServiceImpl();
        List<String> list = Arrays.asList(
                HEAD_FILE,
                INVALID_BALANCE_STRING,
                VALID_SUPPLY_ORANGE);
        assertThrows(IllegalArgumentException.class, ()
                -> parseService.parse(list));

    }
}
