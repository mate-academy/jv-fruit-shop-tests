package core.basesyntax.services;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParseServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
