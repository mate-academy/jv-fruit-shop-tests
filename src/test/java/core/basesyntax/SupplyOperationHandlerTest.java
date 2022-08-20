package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler supplyOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruitType("orange");
        fruitTransaction.setFruitQuantity(20);
    }

    @Test
    public void supplyWithNoSuchFruitInStorage_Ok() {
        try {
            supplyOperationHandler.handle(fruitTransaction);
        } catch (NoSuchElementException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void supplyWithSuchFruitInStorage_Ok() {
        Storage.fruits.add(new Fruit("orange", 10));
        supplyOperationHandler.handle(fruitTransaction);
        Fruit expected = new Fruit("orange", 30);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
