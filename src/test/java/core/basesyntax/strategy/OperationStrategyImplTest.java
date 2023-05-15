package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static final Map<Operation, OperationHandler> strategyMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        strategy = new OperationStrategyImpl(strategyMap);
        
        strategyMap.put(Operation.BALANCE, new BalanceOperationHandler());
        strategyMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        strategyMap.put(Operation.RETURN, new ReturnOperationHandler());
        strategyMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }

    @Test
    public void get_normal_Ok() {
        assertEquals(BalanceOperationHandler.class, strategy.get(Operation.BALANCE).getClass());
        assertEquals(SupplyOperationHandler.class, strategy.get(Operation.SUPPLY).getClass());
        assertEquals(PurchaseOperationHandler.class, strategy.get(Operation.PURCHASE).getClass());
        assertEquals(ReturnOperationHandler.class, strategy.get(Operation.RETURN).getClass());
    }

    @Test
    public void get_null_Ok() {
        assertEquals(BalanceOperationHandler.class, strategy.get(null).getClass());
    }

    @After
    public void tearDown() {
        
    } 
}
