package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private FruitTransactionParser parser;

    @BeforeEach
    void setUp() {
        parser = new FruitTransactionParser();
    }

    @Test
    void parse_ValidInput_ReturnsCorrectTransactions_Ok() {
        List<String> lines = Arrays.asList("b,banana,50", "s,apple,20");
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20)
        );

        List<FruitTransaction> actual = parser.parse(lines);

        assertEquals(expected, actual);
    }
}
