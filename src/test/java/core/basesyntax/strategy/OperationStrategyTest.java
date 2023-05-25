package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationServiceImpl;
import core.basesyntax.strategy.impl.PurchaseOperationServiceImpl;
import core.basesyntax.strategy.impl.ReturnOperationServiceImpl;
import core.basesyntax.strategy.impl.SupplyOperationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategy(PRODUCT_DAO);
    }

    @Test
    void getOperation_getBalanceOperation_ok() {
        OperationService expected = new BalanceOperationServiceImpl(PRODUCT_DAO);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getReturnOperation_ok() {
        OperationService expected = new ReturnOperationServiceImpl(PRODUCT_DAO);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getSupplyOperation_ok() {
        OperationService expected = new SupplyOperationServiceImpl(PRODUCT_DAO);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperation_getPurchaseOperation_ok() {
        OperationService expected = new PurchaseOperationServiceImpl(PRODUCT_DAO);
        OperationService actual = operationStrategy.getOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        assertEquals(expected.getClass(), actual.getClass());
    }
}
