package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final List<String> DATA_FROM_FILE = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "s,banana,50");
    private TransactionParser parser = new TransactionParserImpl();

    @Test
    public void parseTransactions_validData_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = parser.parseTransactions(DATA_FROM_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTransactions_emptyList_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = parser.parseTransactions(Collections.emptyList());
        assertEquals(expected, actual);
    }
}
