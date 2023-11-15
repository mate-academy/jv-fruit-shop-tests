package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.impl.ProductDaoImpl;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PursheOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static ProductDao productDao;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        operationStrategy = new OperationStrategy(productDao);
    }

    @Test
    void getOperation_getBalanceOperation_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.BALANCE, "banana", 10));
        assertEquals(expected, actual.getClass());
    }

    @Test
    void getOperation_getReturnOperation_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.RETURN, "banana", 10));
        assertEquals(expected, actual.getClass());
    }

    @Test
    void getOperation_getSupplyOperation_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.SUPPLY, "banana", 10));
        assertEquals(expected, actual.getClass());
    }

    @Test
    void getOperation_getPurchaseOperation_ok() {
        Class<PursheOperationHandler> expected = PursheOperationHandler.class;
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.PURCHASE, "banana", 10));
        assertEquals(expected, actual.getClass());
    }

    @Test
    void get_getNulOperationStrategy_notOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.getOperation(null));
    }
}
