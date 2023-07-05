package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.OperationDao;
import core.basesyntax.dao.impl.OperationDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationBalanceHandlerTest {
    private OperationHandler operationHandler;
    private OperationDao operationDao;
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @BeforeEach
    public void setUp() {
        fruitTransaction.setFruit("banana");
        operationDao = new OperationDaoImpl();
        operationHandler = new OperationBalanceHandler();
        operationDao.put("banana", 20);
    }

    @Test
    public void test_OperationBalance_Ok() {
        fruitTransaction.setQuantity(10);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(10, operationDao.get("banana"));
        fruitTransaction.setQuantity(5);
        operationHandler.processOperation(fruitTransaction);
        assertEquals(5, operationDao.get("banana"));
    }

    @Test
    public void test_OperationBalance_NegativeQuantity_Ok() {
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> operationHandler.processOperation(fruitTransaction));
    }
}
