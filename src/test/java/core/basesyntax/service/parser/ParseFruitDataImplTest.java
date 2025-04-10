package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseFruitDataImplTest {
    private ParseFruitData parser;

    @BeforeEach
    void setUp() {
        parser = new ParseFruitDataImpl();
    }

    @Test
    void testParseValidData_Ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> result = parser.parseData(input);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void testParseEmptyData_Ok() {
        List<String> input = List.of("type,fruit,quantity");

        List<FruitTransaction> result = parser.parseData(input);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseInvalidFormat_NotOk() {
        List<String> input = List.of("type1,type2,fruit,quantity",
                "r,b,apple,200");
        assertThrows(IllegalArgumentException.class,() -> parser.parseData(input));
    }

    @Test
    void testParseEmptyData_NotOk() {
        List<String> input = List.of();
        assertThrows(IllegalArgumentException.class,() -> parser.parseData(input));
    }

    @Test
    void testParseInvalidOperation_NotOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,20"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parseData(input));
    }

    @Test
    void testParseInvalidQuantity_NotOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parseData(input));
    }

}
