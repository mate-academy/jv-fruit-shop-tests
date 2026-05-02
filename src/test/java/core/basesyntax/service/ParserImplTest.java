package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.Parser;
import core.basesyntax.basesyntax.service.impl.ParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parse_validInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,30",
                "s,grapes,10",
                "p,banana,5",
                "r,apple,2"
        );

        List<FruitTransaction> result = parser.parse(input);

        assertEquals(4, result.size());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals("grapes", result.get(1).getFruit());
    }

    @Test
    void parse_invalidOperation_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,apple,12"
        );
        assertThrows(RuntimeException.class, () -> parser.parse(input));
    }

    @Test
    void parse_invalidFormat_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "p,orange"
        );
        assertThrows(RuntimeException.class, () -> parser.parse(input));
    }

    @Test
    void parse_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parse(null));
    }
}
