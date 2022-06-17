package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.Purchase;
import core.basesyntax.strategy.impl.ReturnFruit;
import core.basesyntax.strategy.impl.Supply;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    
    @BeforeClass
    public static void initOperationStrategy() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new Purchase());
        operationHandlerMap.put(Operation.RETURN, new ReturnFruit());
        operationHandlerMap.put(Operation.SUPPLY, new Supply());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }
    
    @Test
    public void get_purchaseClas_ok() {
        Class<Purchase> purchaseClass = Purchase.class;
        OperationHandler purchaseOperation = operationStrategy.get(Operation.PURCHASE);
        assertEquals(purchaseOperation.getClass(), purchaseClass);
    }

    @Test
    public void get_BalanceClas_ok() {
        Class<BalanceOperationHandler> balanceClass
                = BalanceOperationHandler.class;
        OperationHandler balanceOperation = operationStrategy.get(Operation.BALANCE);
        assertEquals(balanceOperation.getClass(), balanceClass);
    }

    @Test
    public void get_ReturnClas_ok() {
        Class<ReturnFruit> returnClass = ReturnFruit.class;
        OperationHandler returnOperation = operationStrategy.get(Operation.RETURN);
        assertEquals(returnOperation.getClass(), returnClass);
    }

    @Test
    public void get_supplyClas_ok() {
        Class<Supply> supplyClass = Supply.class;
        OperationHandler supplyOperation = operationStrategy.get(Operation.SUPPLY);
        assertEquals(supplyOperation.getClass(), supplyClass);
    }
}
