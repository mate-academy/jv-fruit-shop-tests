package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserTest {
    private TransactionParser transactionParser;
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private List<String> activities;

    @Before
    public void setUp() {
        transactionParser = new TransactionParser();
        fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(100);
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(10);
        activities = new ArrayList<>();
        activities.add("type,fruit,quantity");
        activities.add("s,banana,100");
        activities.add("r,apple,10");
    }

    @Test
    public void parseTransactions_ok() {
        List<FruitTransaction> actualList = transactionParser.parseTransactions(activities);
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains(fruitTransaction1));
        assertTrue(actualList.contains(fruitTransaction2));
    }

    @Test (expected = NumberFormatException.class)
    public void parseTransactions_quantityNotNumber_notOk() {
        activities.add("r,apple,sos");
        transactionParser.parseTransactions(activities);
    }

    @Test (expected = NullPointerException.class)
    public void parseTransactions_nullActivities_notOk() {
        activities.add(null);
        transactionParser.parseTransactions(activities);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parseTransactions_emptyActivities_notOk() {
        transactionParser.parseTransactions(new ArrayList<>());
    }
}
