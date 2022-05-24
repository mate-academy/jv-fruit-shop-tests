package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static FruitTransaction fruitTransaction;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        final String fruit = "apple";
        final int quantity = 10;
        final FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(operation);
        exception = new Exception();
    }

    @Test
    public void add_nullFruitTransaction_notOK() {
        try {
            fruitDao.add(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
        Assert.assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void add_nullFruitName_notOK() {
        fruitTransaction.setFruit(null);
        try {
            fruitDao.add(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
        Assert.assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void add_fruitNameWithNonLetters_notOK() {
        fruitTransaction.setFruit("apple1");
        try {
            fruitDao.add(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
        Assert.assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void add_nullOperation_notOK() {
        fruitTransaction.setOperation(null);
        try {
            fruitDao.add(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
        Assert.assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void add_negativeQuantity_notOK() {
        fruitTransaction.setQuantity(-1);
        try {
            fruitDao.add(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
        Assert.assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void add_goodData_OK() {
        try {
            fruitDao.add(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(1, Storage.fruits.size());
    }

    @Test
    public void get_nullFruitName_notOK() {
        fruitDao.add(fruitTransaction);
        try {
            fruitDao.get(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void get_fruitNameWithNonLetters_notOK() {
        fruitDao.add(fruitTransaction);
        try {
            fruitDao.get("apple1");
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getAll_emptyStorage_OK() {
        List<FruitTransaction> fruitTransactionList = fruitDao.getAll();
        Assert.assertEquals(0, fruitTransactionList.size());
    }

    @Test
    public void getAll_nonEmptyStorage_OK() {
        fruitDao.add(fruitTransaction);
        List<FruitTransaction> fruitList = fruitDao.getAll();
        Assert.assertEquals(1, fruitList.size());
        Assert.assertEquals("apple", fruitList.get(0).getFruit());
        Assert.assertEquals(10, fruitList.get(0).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitList.get(0).getOperation());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
