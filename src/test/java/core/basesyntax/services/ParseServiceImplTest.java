package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {

    private ParseServiceImpl parseService;

    @BeforeEach
    public void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void test_ParseWithNullInput_Ok() {
        List<FruitTransaction> resultFruit = parseService.parse(null);
        assertNotNull(resultFruit);
        assertTrue(resultFruit.isEmpty());
    }

    @Test
    public void test_ParseWithEmptyInput() {
        List<FruitTransaction> resultFruit = parseService.parse(Collections.emptyList());
        assertNotNull(resultFruit);
        assertTrue(resultFruit.isEmpty());
    }

    @Test
    public void test_ParseInvalidInput_notOk() {
        List<String> list = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,5A",
                "s,orange,5");
        assertThrows(IllegalArgumentException.class, () -> parseService.parse(list));

    }
}
