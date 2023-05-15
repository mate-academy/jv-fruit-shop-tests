package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserTest {
    private TransactionParser transactionParser = new TransactionParser();
    private FruitTransaction transactionOne;
    private FruitTransaction transactionTwo;
    private List<String> activities;

    @Before
    public void setUp() {
        transactionOne = new FruitTransaction();
        transactionOne.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionOne.setFruit("banana");
        transactionOne.setQuantity(100);
        transactionTwo = new FruitTransaction();
        transactionTwo.setOperation(FruitTransaction.Operation.RETURN);
        transactionTwo.setFruit("apple");
        transactionTwo.setQuantity(10);
        activities = new ArrayList<>();
        activities.add("type,fruit,quantity");
        activities.add("s,banana,100");
        activities.add("r,apple,10");
    }

    @Test
    public void parseTransactions_ok() {
        List<FruitTransaction> actualList = transactionParser.parseTransactions(activities);
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains(transactionOne));
        assertTrue(actualList.contains(transactionTwo));
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
