package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.TransactionParser;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parse_Ok() {
        String data = "b,banana,20";
        Transaction actual = transactionParser.parse(data);
        Transaction expected = new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 20);
        assertEquals(expected, actual);
    }
}
