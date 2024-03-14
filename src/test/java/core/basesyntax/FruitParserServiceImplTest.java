package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.impl.FruitParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitParserServiceImplTest {
    private final ParserService parserService = new FruitParserServiceImpl();

    @Test
    void parse_validInput_success() {
        List<String> commands = List.of("b,banana,20", "s,apple,100");
        List<FruitTransaction> transactions = parserService.parse(commands);

        assertEquals(2, transactions.size());
        assertEquals(Operation.BALANCE, transactions.get(0).operation());
        assertEquals("banana", transactions.get(0).fruit());
        assertEquals(20, transactions.get(0).quantity());
        assertEquals(Operation.SUPPLY, transactions.get(1).operation());
        assertEquals("apple", transactions.get(1).fruit());
        assertEquals(100, transactions.get(1).quantity());
    }

    @Test
    void parse_invalidInput_throwsException() {
        List<String> commands = List.of("x,apple,-10");
        assertThrows(IllegalArgumentException.class, () -> parserService.parse(commands));
    }

    @Test
    void parse_incompleteData_throwsException() {
        List<String> commands = List.of("b,banana");
        assertThrows(RuntimeException.class, () -> parserService.parse(commands));
    }
}
