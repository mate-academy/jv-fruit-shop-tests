package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private Parser parser = new ParserImpl();
    private List<String> goodLines = List.of(
            "b,banana,200",
            "p,banana,10"
    );
    private List<String> badLines = List.of(
            "b,banana",
            "p,banana,10"
    );

    @Test
    void parse_ok() {
        List<Instruction> expected = List.of(
                new Instruction(FruitOperation.BALANCE, "banana", 200),
                new Instruction(FruitOperation.PURCHASE, "banana", 10)
        );
        assertEquals(expected, parser.parse(goodLines),
                "Wrong work of parse method");
    }

    @Test
    void parse_badLine_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parse(badLines),
                "Method parse shouldn't create Instruction from such lines" + badLines.get(1));
    }
}
