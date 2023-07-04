package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.OperationDao;
import core.basesyntax.dao.impl.OperationDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationReturnHandlerTest {
    private OperationHandler operationHandler;
    private OperationDao operationDao;

    @BeforeEach
    public void setUp() {
        operationDao = new OperationDaoImpl();
        operationHandler = new OperationReturnHandler();
        operationDao.put("banana", 20);
    }

    @Test
    public void test_OperationReturn_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(30, operationDao.get("banana"));
        fruitTransaction.setQuantity(5);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(35, operationDao.get("banana"));
    }

    @Test
    public void test_OperationReturn_NotFruitInDB_NotOk() {
        assertEquals(0, operationDao.get("apple"));
    }

    @Test
    public void test_OperationReturn_FruitInDB_Ok() {
        assertNotNull(operationDao.get("banana"));
        assertNotEquals(0, operationDao.get("banana"));
        assertEquals(20, operationDao.get("banana"));
    }

    @Test
    public void test_OperationReturn_NegativeQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }
}
