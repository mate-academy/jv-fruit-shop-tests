package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private final ParserImpl parser = new ParserImpl();

    @Test
    void parse_validInput_Ok() {
        final String line0 = "type,fruit,quantity";
        final String line1 = "b ,banana, 20";
        final String lineLast = "s,banana,50";
        List<String> lines = List.of(line0, line1, lineLast);
        List<FruitTransaction> fruitTransactions =
                List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                        new FruitTransaction(Operation.SUPPLY, "banana", 50));
        assertEquals(fruitTransactions, parser.parse(lines));
    }

    @Test
    void parse_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null),
                "IllegalArgumentException expected");
    }
}
