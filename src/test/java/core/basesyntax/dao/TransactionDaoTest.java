package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
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
    public void addTransaction_FruitTransaction_Ok() {
        transactionDao.addTransaction(fruitTransaction);
        assertTrue(Storage.fruitTransactions.contains(fruitTransaction));
    }

    @Test
    public void addFruit_Fruit_Ok() {
        transactionDao.addFruit(fruit);
        assertTrue(Storage.fruits.contains(fruit));
    }

    @Test
    public void getFruitByType_String_Ok() {
        Fruit actual = transactionDao.getFruit("apple");
        assertEquals(actual, fruit);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
        Storage.fruitTransactions.clear();
    }
}
