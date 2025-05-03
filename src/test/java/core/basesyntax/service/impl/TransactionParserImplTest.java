package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser = new TransactionParserImpl();
    private List<String> csvRows = new ArrayList<>();

    @Test
    public void parseTransactions_ok() {
        csvRows.add("type,fruit,quantity");
        csvRows.add("b,mango,20");
        csvRows.add("p,apple,10");
        csvRows.add("r,apple,1");

        List<FruitTransaction> transactions = transactionParser.parseTransactions(csvRows);

        assertEquals(3, transactions.size());

        assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals(new Fruit("mango"), transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals(new Fruit("apple"), transactions.get(1).getFruit());
        assertEquals(10, transactions.get(1).getQuantity());

        assertEquals(Operation.RETURN, transactions.get(2).getOperation());
        assertEquals(new Fruit("apple"), transactions.get(2).getFruit());
        assertEquals(1, transactions.get(2).getQuantity());
    }

    @Test
    public void parseTransactionsWithEmptyInput() {
        csvRows.add("type,fruit,quantity");
        List<FruitTransaction> transactions = transactionParser
                .parseTransactions(csvRows);

        assertTrue(transactions.isEmpty());
    }

    @Test
    public void failedToParseTransactionsForInvalidOperation() {
        csvRows.add("type,fruit,quantity");
        csvRows.add("x,apple,10");

        assertThrows(IllegalArgumentException.class, () -> transactionParser
                .parseTransactions(csvRows));
    }
}
