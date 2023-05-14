package core.basesyntax.service.strategy;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new OperationHandlerOfBalance(),
                FruitTransaction.Operation.SUPPLY,
                new OperationHandlerOfSupply(),
                FruitTransaction.Operation.PURCHASE,
                new OperationHandlerOfPurchase(),
                FruitTransaction.Operation.RETURN,
                new OperationHandlerOfReturn());
        operationStrategy = new OperationStrategyImpl(operationServiceMap);
    }

    @Test
    public void getBalance_ok() {
        assertEquals(OperationHandlerOfBalance.class,
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void getPurchase_ok() {
        assertEquals(OperationHandlerOfPurchase.class,
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void getReturn_ok() {
        assertEquals(OperationHandlerOfReturn.class,
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    public void getSupply_ok() {
        assertEquals(OperationHandlerOfSupply.class,
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }
}
