package core.basesyntax.serviceparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {

    private final FruitTransactionParserImpl parser = new FruitTransactionParserImpl();

    @Test
    public void testParse_ValidLines() {
        List<String> lines = Arrays.asList(
                "BALANCE,apple,10",
                "SUPPLY,banana,5",
                "PURCHASE,apple,3"
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(3, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("apple", transactions.get(2).getFruit());
        assertEquals(3, transactions.get(2).getQuantity());
    }

    @Test
    public void testParse_InvalidOperation() {
        List<String> lines = Arrays.asList(
                "BALANCE,apple,10",
                "INVALID_OP,banana,5"
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(1, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    public void testParse_InvalidQuantity() {
        List<String> lines = Arrays.asList(
                "BALANCE,apple,10",
                "SUPPLY,banana,abc"
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(1, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    public void testParse_EmptyLine() {
        List<String> lines = Arrays.asList(
                "BALANCE,apple,10",
                "",
                "SUPPLY,banana,5"
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
    }

    @Test
    public void testParse_HeaderLine() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "BALANCE,apple,10",
                "SUPPLY,banana,5"
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
    }
}
