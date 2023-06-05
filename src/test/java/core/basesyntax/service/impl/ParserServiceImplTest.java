package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserServiceImplTest {
    private static ParserService parser;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserServiceImpl();
    }

    @Test
    void parse_validInputData_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100)
        );
        List<String> input = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<FruitTransaction> actual = parser.parse(input);
        assertIterableEquals(expected, actual);
    }

    @Test
    void parse_notValidLengthOfInputLine_notOk() {
        List<String> input = List.of(
                "b,banana",
                "b,apple,100",
                "s,banana,100"
        );
        assertThrows(RuntimeException.class,
                () -> parser.parse(input));
    }

    @Test
    void parse_notValidInputOperationType_notOk() {
        List<String> input = List.of(
                "d,banana, 20",
                "b,apple,100",
                "s,banana,100"
        );
        assertThrows(RuntimeException.class,
                () -> parser.parse(input));
    }

    @Test
    void parse_emptyInput_ok() {
        List<String> input = Collections.emptyList();
        List<FruitTransaction> actual = parser.parse(input);
        assertTrue(actual.isEmpty());
    }
}
