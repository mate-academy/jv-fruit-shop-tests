package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.NoSuchElementException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDaoTest {
    private static TransactionDao transactionDao;
    private static FruitTransaction fruitTransaction;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        transactionDao = new TransactionDaoImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(2);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruit = new Fruit("apple", 10);
    }

    @Test
    public void addTransaction_Ok() {
        transactionDao.addTransaction(fruitTransaction);
        assertTrue(Storage.fruitTransactions.contains(fruitTransaction));
    }

    @Test
    public void addFruit_Ok() {
        transactionDao.addFruit(fruit);
        assertTrue(Storage.fruits.contains(fruit));
    }

    @Test
    public void getFruit_Ok() {
        Fruit actual = transactionDao.getFruit("apple");
        assertEquals(actual, fruit);
    }

    @Test(expected = NoSuchElementException.class)
    public void getFruit_notFound_notOk() {
        transactionDao.getFruit("orange");
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
        Storage.fruitTransactions.clear();
    }
}
