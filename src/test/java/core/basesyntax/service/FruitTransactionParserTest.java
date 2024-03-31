package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
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
    void parse_ValidInput_ShouldReturnCorrectResult() {
        List<String> lines = Arrays.asList("b,banana,50", "s,apple,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20)
        );

        List<FruitTransaction> actual = parser.parse(lines);

        assertEquals(expected, actual);
    }

    @Test
    void parse_InvalidOperationCode_ShouldThrowException() {
        List<String> lines = Collections.singletonList("x,banana,50");

        assertThrows(IllegalArgumentException.class, () -> parser.parse(lines));
    }
}
