package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private TransactionService transactionService;
    private List<String> elementsInFile;

    private FruitTransaction fruitTransaction;

    private FruitTransaction fruitTransaction2;

    @Before
    public void setUp() {
        transactionService = new TransactionServiceImpl();
        elementsInFile = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
        fruitTransaction2 = new FruitTransaction();
        elementsInFile.add("b,banana,20");
        elementsInFile.add("b,apple,100");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);
    }

    @Test
    public void convertStringToFruitTransaction_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction);
        expected.add(fruitTransaction2);
        List<FruitTransaction> actual
                = transactionService.convertStringToFruitTransaction(elementsInFile);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void convertString_emptyElements_notOk() {
        List<String> elementsInFile = new ArrayList<>();
        transactionService.convertStringToFruitTransaction(elementsInFile);
    }

    @Test(expected = RuntimeException.class)
    public void convertString_nullElements_notOk() {
        transactionService.convertStringToFruitTransaction(null);
    }
}
