package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitTransaction fruitTransaction;
    private static OperationStrategyImpl operationStrategy;
    private static Map<FruitTransaction.Operation,
            OperationHandler> fruitServiceMap = new HashMap<>();

    static {
        fruitServiceMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
    }

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl(fruitServiceMap);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void get_operationBalance_ok() {
        Class<? extends OperationHandler> expectedClass = BalanceOperationHandler.class;
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        OperationHandler currentHandler = operationStrategy.get(fruitTransaction.getOperation());
        assertEquals(expectedClass, currentHandler.getClass());
    }

    @Test
    public void get_operationSupply_ok() {
        Class<? extends OperationHandler> expectedClass = SupplyOperationHandler.class;
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        OperationHandler currentHandler = operationStrategy.get(fruitTransaction.getOperation());
        assertEquals(expectedClass, currentHandler.getClass());
    }

    @Test
    public void get_operationPurchase_ok() {
        Class<? extends OperationHandler> expectedClass = PurchaseOperationHandler.class;
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        OperationHandler currentHandler = operationStrategy.get(fruitTransaction.getOperation());
        assertEquals(expectedClass, currentHandler.getClass());
    }

    @Test
    public void get_operationReturn_ok() {
        Class<? extends OperationHandler> expectedClass = ReturnOperationHandler.class;
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        OperationHandler currentHandler = operationStrategy.get(fruitTransaction.getOperation());
        assertEquals(expectedClass, currentHandler.getClass());
    }
}
