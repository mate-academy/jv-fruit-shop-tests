package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parseStringToModel_Ok() {
        List<String> fruitList = new ArrayList<>();
        fruitList.add("b,apple,20");
        fruitList.add("s,banana,30");
        fruitList.add("r,apple,10");
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(20);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(30);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction3.setFruit("apple");
        fruitTransaction3.setQuantity(10);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction1);
        fruitTransactionList.add(fruitTransaction2);
        fruitTransactionList.add(fruitTransaction3);
        Assert.assertEquals(fruitTransactionList, transactionParser.parseAll(fruitList));
    }

    @Test(expected = RuntimeException.class)
    public void wrongOperation_NotOk() {
        List<String> fruit = new ArrayList<>();
        fruit.add("b,apple,20");
        fruit.add("s,banana,30");
        fruit.add("n,apple,10");
        transactionParser.parseAll(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void emptySpaceOperation_NotOk() {
        List<String> fruit = new ArrayList<>();
        fruit.add("b,apple,20");
        fruit.add("s,banana,30");
        fruit.add(",apple,10");
        transactionParser.parseAll(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void emptySpaces_NotOk() {
        List<String> fruit = new ArrayList<>();
        fruit.add(",,");
        transactionParser.parseAll(fruit);
    }
}
