package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private FruitTransaction fruitTransaction3;
    private List<String> activities;

    @Before
    public void setTransactions() {
        fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setQuantity(20);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setQuantity(100);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setQuantity(13);
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        activities = new ArrayList<>();
        activities.add("type,fruit,quantity");
        activities.add("b,banana,20");
        activities.add("b,apple,100");
        activities.add("p,banana,13");
    }

    @Test
    public void parseString_ok() {
        List<FruitTransaction> actualList = transactionParser.parse(activities);
        List<FruitTransaction> expectedList = List
                .of(fruitTransaction1, fruitTransaction2, fruitTransaction3);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseString_notOk() {
        activities.add("s, banana, ss");
        assertThrows(NumberFormatException.class, () -> transactionParser.parse(activities));
    }

    @Test
    public void parseStringWithNullValue_notOk() {
        activities.add(null);
        assertThrows(NullPointerException.class, () -> transactionParser.parse(activities));
    }
}
