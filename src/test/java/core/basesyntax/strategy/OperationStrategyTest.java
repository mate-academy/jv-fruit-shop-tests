package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.BalanceOperationServiceImpl;
import core.basesyntax.strategy.impl.PurchaseOperationServiceImpl;
import core.basesyntax.strategy.impl.ReturnOperationServiceImpl;
import core.basesyntax.strategy.impl.SupplyOperationServiceImpl;
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
        OperationService expected = new BalanceOperationServiceImpl(productDao);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.BALANCE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getReturnOperation_ok() {
        OperationService expected = new ReturnOperationServiceImpl(productDao);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.RETURN, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getSupplyOperation_ok() {
        OperationService expected = new SupplyOperationServiceImpl(productDao);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.SUPPLY, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getPurchaseOperation_ok() {
        OperationService expected = new PurchaseOperationServiceImpl(productDao);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(Operation.PURCHASE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }
}
