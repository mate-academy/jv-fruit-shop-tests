package service.strategy;

import static org.junit.Assert.assertEquals;

import dao.StorageDaoImpl;
import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void apply_correctTransaction_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 40);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
        int expected = 8;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongTransaction_notOk() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
