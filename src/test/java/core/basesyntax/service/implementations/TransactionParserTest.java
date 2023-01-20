package core.basesyntax.service.implementations;

import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class TransactionParserTest {
    private TransactionParser transactionParser = new TransactionParser();
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private List<String> activities;

    @BeforeEach
    public void setUp() {
        fruitTransaction1= new FruitTransaction();
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
        Assert.assertEquals(2, actualList.size());
        Assert.assertTrue(actualList.contains(fruitTransaction1));
        Assert.assertTrue(actualList.contains(fruitTransaction2));
    }

    @Test
    public void parseTransactions_quantityNotNumber_notOk() {
        activities.add("r,apple,sos");
        Assert.assertThrows(NumberFormatException.class, () -> transactionParser.parseTransactions(activities));
    }

    @Test
    public void parseTransactions_nullActivities_notOk() {
        activities.add(null);
        Assert.assertThrows(NullPointerException.class, () -> transactionParser.parseTransactions(activities));
    }

    @Test
    public void parseTransactions_emptyActivities_notOk() {
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> transactionParser.parseTransactions(new ArrayList<>()));
    }

}