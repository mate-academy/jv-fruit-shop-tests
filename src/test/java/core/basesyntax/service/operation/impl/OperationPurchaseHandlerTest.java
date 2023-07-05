package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @BeforeEach
    public void setUp() {
        fruitTransaction.setFruit("banana");
        operationDao = new OperationDaoImpl();
        operationHandler = new OperationPurchaseHandler();
        operationDao.put("banana", 20);
        operationDao.put("citrus", 50);
    }

    @Test
    public void test_OperationPurchase_Ok() {
        fruitTransaction.setQuantity(10);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(10, operationDao.get("banana"));
        fruitTransaction.setFruit("citrus");
        fruitTransaction.setQuantity(5);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(45, operationDao.get("citrus"));
    }

    @Test
    public void test_OperationPurchase_NullHandler_NotOk() {
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
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
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
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }
}
