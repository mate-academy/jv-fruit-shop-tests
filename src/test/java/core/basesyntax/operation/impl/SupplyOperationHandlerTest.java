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

public class SupplyOperationHandlerTest {
    private FruitDao fruitDao;
    private FruitService fruitService;
    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
        operationHandler = new SupplyOperationHandler(fruitDao, fruitService);
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
        update.setOperation(FruitTransaction.Operation.RETURN);
        update.setQuantity(10);
        operationHandler.handle(update);
        FruitTransaction expected = new FruitTransaction();
        expected.setFruit("banana");
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setQuantity(110);
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
    public void tearDown() throws Exception {
        Storage.warehouse.clear();
    }
}
