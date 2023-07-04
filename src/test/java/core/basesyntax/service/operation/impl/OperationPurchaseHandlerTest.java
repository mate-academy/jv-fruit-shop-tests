package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.OperationDao;
import core.basesyntax.dao.impl.OperationDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationPurchaseHandlerTest {
    private OperationHandler operationHandler;
    private OperationDao operationDao;

    @BeforeEach
    public void setUp() {
        operationDao = new OperationDaoImpl();
        operationHandler = new OperationPurchaseHandler();
        operationDao.put("banana", 20);
        operationDao.put("citrus", 50);
    }

    @Test
    public void test_OperationPurchase_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(10, operationDao.get("banana"));
        fruitTransaction.setFruit("citrus");
        fruitTransaction.setQuantity(5);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(45, operationDao.get("citrus"));
    }

    @Test
    public void test_OperationPurchase_NotFruitInDB_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertEquals(0, operationDao.get("apple"));
    }

    @Test
    public void test_OperationPurchase_FruitInDB_Ok() {
        assertNotNull(operationDao.get("banana"));
        assertEquals(20, operationDao.get("banana"));
    }

    @Test
    public void test_OperationPurchase_FruitNotInDB_NotOk() {
        assertEquals(0, operationDao.get("apple"));
    }

    @Test
    public void test_OperationPurchase_NullHandler_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        operationHandler = null;
        assertThrows(NullPointerException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }

    @Test
    public void test_OperationPurchase_NullTransaction_NotOk() {
        FruitTransaction fruitTransaction = null;
        assertThrows(NullPointerException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }

    @Test
    public void test_OperationPurchase_NotFruitQuantityToSell_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(35);
        String expected = ("There are not enough products to sell: "
                + "p," + fruitTransaction.getFruit()
                + "," + fruitTransaction.getQuantity());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.processOperation(fruitTransaction));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_OperationPurchase_NegativeQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }
}
