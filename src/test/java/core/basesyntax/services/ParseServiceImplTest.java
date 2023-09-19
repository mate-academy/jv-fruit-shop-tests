package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {
    private static final String HEAD_FILE = "operation,fruit,quantity";
    private static final String INVALID_BALANCE_STRING = "b,apple,5A";
    private static final String VALID_SUPPLY_ORANGE = "s,orange,5";

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
