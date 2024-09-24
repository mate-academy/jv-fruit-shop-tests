package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private final TransactionParser parser = new TransactionParserImpl();

    @Test
    void parse_ValidInput_Ok() {
        List<String> lines = List.of(
                "b,banana,20",
                "s,apple,30",
                "p,banana,10",
                "r,apple,5"
        );

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "apple", 30),
                new FruitTransaction(Operation.PURCHASE, "banana", 10),
                new FruitTransaction(Operation.RETURN, "apple", 5)
        );

        assertEquals(expected, parser.parse(lines));
    }

    @Test
    void parse_InvalidOperation_ThrowsException() {
        List<String> lines = List.of(
                "x,banana,20"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> parser.parse(lines));
        assertEquals("Invalid operation: x", exception.getMessage());
    }
}
