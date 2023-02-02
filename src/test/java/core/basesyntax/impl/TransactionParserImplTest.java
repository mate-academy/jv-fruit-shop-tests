package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private FruitTransaction firstFruitTransaction;
    private FruitTransaction secondFruitTransaction;
    private FruitTransaction thirdFruitTransaction;
    private List<String> activities;

    @BeforeClass
    public static void initialize_var() {
        transactionParser = new TransactionParserImpl();
    }

    @Before
    public void setTransactions() {
        firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setQuantity(20);
        firstFruitTransaction.setFruit("banana");
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setQuantity(100);
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        thirdFruitTransaction = new FruitTransaction();
        thirdFruitTransaction.setQuantity(13);
        thirdFruitTransaction.setFruit("banana");
        thirdFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
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
                .of(firstFruitTransaction, secondFruitTransaction, thirdFruitTransaction);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseString_notOk() {
        activities.add("s, banana, ss");
        assertThrows(NumberFormatException.class, () -> transactionParser.parse(activities));
    }
}
