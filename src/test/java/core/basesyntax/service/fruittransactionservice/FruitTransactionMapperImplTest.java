package core.basesyntax.service.fruittransactionservice;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionMapperImplTest {
    private FruitTransactionMapper fruitTransactionMapper = new FruitTransactionMapperImpl();
    private List<String> lines = new ArrayList<>();

    @After
    public void afterEachTest() {
        lines.clear();
    }

    @Test
    public void getFruitTransactionsWithAllPossibleProcesses() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("r,apple,80");
        lines.add("s,orange,100");
        lines.add("p,kiwi,10");
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(20);
        bananaTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(80);
        appleTransaction.setOperation(FruitTransaction.Operation.RETURN);
        FruitTransaction orangeTransaction = new FruitTransaction();
        orangeTransaction.setFruit("orange");
        orangeTransaction.setQuantity(100);
        orangeTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        FruitTransaction kiwiTransaction = new FruitTransaction();
        kiwiTransaction.setFruit("kiwi");
        kiwiTransaction.setQuantity(10);
        kiwiTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        List<FruitTransaction> actualListOfTransactions
                = fruitTransactionMapper.getFruitTransactions(lines);
        List<FruitTransaction> expectedListOfTransactions
                = List.of(bananaTransaction, appleTransaction, orangeTransaction, kiwiTransaction);
        Assert.assertEquals(expectedListOfTransactions, actualListOfTransactions);
    }

    @Test
    public void getFruitTransactionsWithoutFruits() {
        List<FruitTransaction> actualListOfTransactions
                = fruitTransactionMapper.getFruitTransactions(lines);
        List<FruitTransaction> expectedListOfTransactions = new ArrayList<>();
        Assert.assertEquals(expectedListOfTransactions, actualListOfTransactions);
    }
}
