package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;

    @Before
    public void setup() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parseEmpty_ok() {
        List<String> title = List.of(
                "type,fruit,quantity"
        );
        List<FruitTransaction> actual = transactionParser.parse(title);
        assertTrue(actual.isEmpty());
        List<String> empty = new ArrayList<>();
        actual = transactionParser.parse(empty);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void parse_ok() {
        List<String> records = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,apple,10",
                "r,apple,1",
                "s,apple,10",
                "p,apple,50",
                "r,apple,10",
                "s,apple,10",
                "s,apple,10",
                "p,apple,50",
                "p,apple,20"
        );
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(BALANCE, "apple", 100),
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(RETURN, "apple", 1),
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(PURCHASE, "apple", 50),
                new FruitTransaction(RETURN, "apple", 10),
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(PURCHASE, "apple", 50),
                new FruitTransaction(PURCHASE, "apple", 20)
        );
        List<FruitTransaction> actualTransactions = transactionParser.parse(records);
        assertTrue(actualTransactions.size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(expectedTransactions.get(i),
                    actualTransactions.get(i));
        }
    }

    @Test
    public void parseWithEmptyLineInsideList_ok() {
        List<String> records = List.of(
                "",
                "",
                "",
                "s,apple,10",
                "p,apple,50",
                "r,apple,10",
                "",
                "s,apple,10",
                "",
                "s,apple,10",
                ""
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(PURCHASE, "apple", 50),
                new FruitTransaction(RETURN, "apple", 10),
                new FruitTransaction(SUPPLY, "apple", 10),
                new FruitTransaction(SUPPLY, "apple", 10)
        );
        List<FruitTransaction> actual = transactionParser.parse(records);
        assertTrue(actual.size() == 5);
        for (int i = 0; i < 5; i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
