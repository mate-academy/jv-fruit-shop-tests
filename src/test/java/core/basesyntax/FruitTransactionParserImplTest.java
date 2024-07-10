package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private final FruitTransactionParserImpl parser = new FruitTransactionParserImpl();

    @Test
    void parse_validLine_ok() {
        FruitTransaction transaction = parser.parse("s,apple,10");
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals(APPLE, transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void parse_invalidLine_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("invalid_line"));
    }

    @Test
    void parse_invalidOperation_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("x,apple,10"));
    }

    @Test
    void parse_invalidQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("s,apple,ten"));
    }

    @Test
    void parseLines_validLines_ok() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "p,banana,5",
                "r,banana,3"
        );
        List<FruitTransaction> transactions = parser.parseLines(lines);
        assertEquals(3, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals(BANANA, transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals(BANANA, transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(2).getOperation());
        assertEquals(BANANA, transactions.get(2).getFruit());
        assertEquals(3, transactions.get(2).getQuantity());
    }
}
