package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FruitTransactionParserImplTest {
    private final FruitTransactionParserImpl parser = new FruitTransactionParserImpl();

    @Test
    void parse_validLine() {
        FruitTransaction transaction = parser.parse("s,apple,10");
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void parse_invalidLine() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("invalid_line"));
    }

    @Test
    void parse_invalidOperation() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("x,apple,10"));
    }

    @Test
    void parse_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("s,apple,ten"));
    }

    @Test
    void parseLines_validLines() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "p,banana,5",
                "r,banana,3"
        );
        List<FruitTransaction> transactions = parser.parseLines(lines);
        assertEquals(3, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(2).getOperation());
        assertEquals("banana", transactions.get(2).getFruit());
        assertEquals(3, transactions.get(2).getQuantity());
    }
}

