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
    public void parse_withNullInput_ok() {
        result = parser.parse(null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void parse_withEmptyInput_ok() {
        result = parser.parse(Collections.emptyList());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void parse_withValidInput_ok() {
        List<String> input
                = Arrays.asList("operation,fruit,quantity",
                "b,apple,10",
                "s,orange,5");
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "orange",
                        5)
        );
        result = parser.parse(input);
        assertEquals(expected, result);
    }

    @Test
    public void parse_withInvalidInput_ok() {
        List<String> input
                = Arrays.asList("operation,fruit,quantity",
                "b,apple",
                "s,orange,5");
        result = parser.parse(input);
        assertEquals(1, result.size());
    }
}
