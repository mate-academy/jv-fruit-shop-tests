package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.TransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static List<String> fruitInfo;
    private static TransactionService transactionService;
    private static FruitTransaction firstTransaction;
    private static FruitTransaction secondTransaction;
    private static FruitTransaction thirdTransaction;
    private static List<FruitTransaction> transactionList;

    @BeforeClass
    public static void beforeClass() {
        fruitInfo = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100");
        firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(100);
        thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(100);
        transactionList = List.of(firstTransaction, secondTransaction, thirdTransaction);
        transactionService = new TransactionServiceImpl(new TransactionDaoImpl());
    }

    @Test
    public void getTransactions_inputFruitInfo_Ok() {
        List<FruitTransaction> actual = transactionService.getTransaction(fruitInfo);
        assertEquals(actual, transactionList);
    }

    @Test(expected = NullPointerException.class)
    public void getTransactions_null_notOk() {
        List<FruitTransaction> actual = transactionService.getTransaction(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitTransactions.clear();
    }
}
