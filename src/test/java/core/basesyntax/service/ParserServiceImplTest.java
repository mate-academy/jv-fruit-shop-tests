package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseTransaction_WithNullArgument_notOk() {
        List<String> argument = null;
        assertThrows(NullPointerException.class, () -> parserService.parseTransaction(argument));
    }

    @Test
    void parseTransaction_WithEmptyArgument_Ok() {
        List<String> argument = Collections.emptyList();
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = parserService.parseTransaction(argument);
        assertEquals(expected, actual);
    }

    @Test
    void parseTransaction_WithValidArgument_Ok() {
        List<String> argument = List.of("b,banana,10","b,apple,10", "s,banana,10", "s,apple,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 10),
                new FruitTransaction(Operation.SUPPLY, "apple", 10));
        List<FruitTransaction> actual = parserService.parseTransaction(argument);
        assertEquals(expected, actual);
    }
}
