package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.ReturnOperationHandler;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler returnOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruitType("orange");
        fruitTransaction.setFruitQuantity(20);
    }

    @Test
    public void returnWithNoSuchFruitInStorage_Ok() {
        try {
            returnOperationHandler.handle(fruitTransaction);
        } catch (NoSuchElementException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void returnWithSuchFruitInStorage_Ok() {
        Storage.fruits.add(new Fruit("orange", 10));
        returnOperationHandler.handle(fruitTransaction);
        Fruit expected = new Fruit("orange", 30);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
