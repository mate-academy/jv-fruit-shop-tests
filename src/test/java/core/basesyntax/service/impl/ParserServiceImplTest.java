package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserService parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_validContent_Ok() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,20",
                "b,banana,80"
        );
        List<FruitTransaction> result = parserService.parse(input);
        assertEquals(2, result.size(), "Two transactions expected, actual:" + result.size());
    }

    @Test
     void parse_negativeQuantity_NotOk() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,-20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> parserService.parse(input),
                "IllegalArgumentException is expected");
    }

    @Test
    void parse_nullArgument_NotOk() {
        assertThrows(NullPointerException.class,
                () -> parserService.parse(null),
                "Null argument not acceptable, NullPointerException is expected");
    }

    @Test
    void parse_invalidOperationType_NotOk() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "a,apple,20",
                "b,banana,80"
        );
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(1, actual.size(), "One transaction expected, actual:" + actual.size());
    }

    @Test
    void parse_quantityAsText_NotOk() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,twenty",
                "b,banana,80"
        );
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(1, actual.size(), "One transaction expected, actual:" + actual.size());
    }
}
