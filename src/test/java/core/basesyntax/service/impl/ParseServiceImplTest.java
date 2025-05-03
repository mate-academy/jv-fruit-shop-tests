package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.ParseService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parse_validOperation_ok() {
        List<String> lines = List.of(
                "b,banana,20",
                "s,banana,100",
                "p,banana,5",
                "r,banana,10"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(OperationType.BALANCE, "banana", 20),
                new FruitTransaction(OperationType.SUPPLY, "banana", 100),
                new FruitTransaction(OperationType.PURCHASE, "banana", 5),
                new FruitTransaction(OperationType.RETURN, "banana", 10)
        );
        List<FruitTransaction> actual = parseService.parse(lines);
        assertEquals(expected, actual);
    }

    @Test
    void parse_invalidOperation_notOk() {
        List<String> lines = List.of("b,apple,100", "s,apple,20",
                "p,apple,30", "t,apple,5");
        assertThrows(RuntimeException.class,
                () -> parseService.parse(lines));
    }

    @Test
    void parse_invalidAmount_notOk() {
        List<String> lines = List.of("b,apple,-100", "s,apple,20",
                "s,apple,-30", "p,apple,5");
        assertThrows(IllegalArgumentException.class,
                () -> parseService.parse(lines));
    }

    @Test
    void parse_invalidInteger_notOk() {
        List<String> lines = List.of("b,apple,100", "s,apple,20",
                "s,apple,30", "p,apple,ten");
        assertThrows(IllegalArgumentException.class,
                () -> parseService.parse(lines));
    }
}
