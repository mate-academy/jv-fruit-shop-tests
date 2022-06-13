package core.basesyntax.operation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private FruitDao fruitDao;
    private FruitService fruitService;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
        operationHandler = new PurchaseOperationHandler(fruitDao, fruitService);
    }

    @Test
    public void handle_updateExistedFruit_ok() {
        FruitTransaction existedInStorage = new FruitTransaction();
        existedInStorage.setFruit("banana");
        existedInStorage.setOperation(FruitTransaction.Operation.BALANCE);
        existedInStorage.setQuantity(100);
        Storage.warehouse.add(existedInStorage);
        FruitTransaction update = new FruitTransaction();
        update.setFruit("banana");
        update.setOperation(FruitTransaction.Operation.PURCHASE);
        update.setQuantity(50);
        operationHandler.handle(update);
        FruitTransaction expected = new FruitTransaction();
        expected.setFruit("banana");
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setQuantity(50);
        FruitTransaction actual = fruitDao.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void handle_parameterNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }

    @Test
    public void handle_parameterNotSetFields_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(new FruitTransaction());
        });
    }

    @Test
    public void handle_quantityNull_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(0);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction);
        });
    }

    @Test
    public void handle_fruitNull_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(33);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(null);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction);
        });
    }

    @Test
    public void handle_operationNull_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(33);
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction);
        });
    }

    @After
    public void tearDown() {
        Storage.warehouse.clear();
    }
}
