package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private OperationHandler operationHandler;
    private final OperationHandlerStrategyImpl operationHandlerStrategy;

    public OperationHandlerStrategyImplTest() {
        Map<FruitTransaction.TypeOperation, OperationHandler> listOperations = new HashMap<>();
        listOperations.put(FruitTransaction.TypeOperation.BALANCE, new BalanceOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.SUPPLY, new SupplyOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.PURCHASE, new PurchaseOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.RETURN, new ReturnOperationHandler());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(listOperations);
    }

    @Test
    public void returnBalanceOperationClass_OK() {
        operationHandler =
                operationHandlerStrategy.chooseOperation(FruitTransaction.TypeOperation.BALANCE);
        assertEquals(operationHandler.getClass(), BalanceOperationHandler.class);
    }

    @Test
    public void returnSupplyOperationClass_OK() {
        operationHandler =
                operationHandlerStrategy.chooseOperation(FruitTransaction.TypeOperation.SUPPLY);
        assertEquals(operationHandler.getClass(), SupplyOperationHandler.class);
    }

    @Test
    public void returnPurchaseOperationClass_OK() {
        operationHandler =
                operationHandlerStrategy.chooseOperation(FruitTransaction.TypeOperation.PURCHASE);
        assertEquals(operationHandler.getClass(), PurchaseOperationHandler.class);
    }

    @Test
    public void returnReturnOperationClass_OK() {
        operationHandler =
                operationHandlerStrategy.chooseOperation(FruitTransaction.TypeOperation.RETURN);
        assertEquals(operationHandler.getClass(), ReturnOperationHandler.class);
    }
}
