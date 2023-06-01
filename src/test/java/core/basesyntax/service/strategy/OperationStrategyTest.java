package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        OperationHandler expected = new BalanceOperationHandler(productDao);
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.BALANCE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getReturnOperation_ok() {
        OperationHandler expected = new ReturnOperationHandler(productDao);
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.RETURN, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getSupplyOperation_ok() {
        OperationHandler expected = new SupplyOperationHandler(productDao);
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.SUPPLY, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getPurchaseOperation_ok() {
        OperationHandler expected = new PursheOperationHandler(productDao);
        OperationHandler actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.PURCHASE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }
}
