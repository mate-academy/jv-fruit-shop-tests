package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.parser.TransactionParser;
import core.basesyntax.service.parser.TransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TransactionParserImplTest {

    private final TransactionParser parser = new TransactionParserImpl();

    @Test
    void parse_validTransactions_success() {
        List<String> lines = List.of("b,apple,100", "p,apple,20");
        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void parse_emptyInput_returnsEmptyList() {
        List<FruitTransaction> result = parser.parse(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_nonNumericQuantity_skipsLine() {
        List<String> lines = List.of("b,apple,ten", "p,apple,5");
        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getQuantity());
    }

    @Test
    void parse_allOperationsSupported_correctParsing() {
        List<String> lines = List.of(
                "b,apple,100",
                "s,apple,50",
                "p,apple,20",
                "r,apple,10"
        );

        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(4, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
    }
}
