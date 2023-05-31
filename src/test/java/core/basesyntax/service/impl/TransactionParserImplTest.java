package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser parser;

    @Before
    public void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    public void parseData_ok() {
        List<String> transactions = new ArrayList<>();
        transactions.add("type,fruit,quantity");
        transactions.add("b,banana,20");
        transactions.add("b,apple,100");
        transactions.add("b,orange,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.BALANCE, "orange", 100));
        List<FruitTransaction> actual = parser.process(transactions);
        assertEquals(expected, actual);
    }
}
