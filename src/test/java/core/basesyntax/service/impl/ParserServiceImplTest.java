package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserServiceImpl parser;

    @BeforeEach
    void setUp() {
        parser = new ParserServiceImpl();
    }

    @Test
    void parse_ok() {
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
        Assertions.assertIterableEquals(expected, actual);
    }
}
