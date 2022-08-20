package core.basesyntax.service.transaction;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.fruittransactionservice.FruitTransactionMapper;
import core.basesyntax.service.fruittransactionservice.FruitTransactionMapperImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionMapperImplTest {
    private static FruitTransactionMapper fruitTransactionMapper;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionMapper = new FruitTransactionMapperImpl();
    }

    @Test
    public void getTransactions_allProcesses_Ok() {
        List<FruitTransaction> actualTransactions
                = fruitTransactionMapper.getFruitTransactions(getData());
        List<FruitTransaction> expectedTransactions = getExpectedTransactions();
        Assert.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void getTransactions_dataEmpty_Ok() {
        List<FruitTransaction> actualTransactions
                = fruitTransactionMapper.getFruitTransactions(new ArrayList<>());
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        Assert.assertEquals(expectedTransactions, actualTransactions);
    }

    private List<FruitTransaction> getExpectedTransactions() {
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
        return List.of(bananaTransaction, appleTransaction, orangeTransaction, kiwiTransaction);
    }

    private List<String> getData() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("r,apple,80");
        lines.add("s,orange,100");
        lines.add("p,kiwi,10");
        return lines;
    }
}
