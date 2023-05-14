package core.basesyntax.strategy;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationTypeHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation,
            OperationTypeHandler> strategyTest = new HashMap<>();
    private static final OperationStrategy operationStrategy =
            new OperationStrategyImpl(strategyTest);
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransaction = new FruitTransaction();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        strategyTest.clear();
    }

    @Test
    public void getHandlerByOperation_sameTypeOperationInTransactionAndStrategyMap_ok() {
        strategyTest.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        operationStrategy.getHandlerByOperation(fruitTransaction.getOperation())
                .handle(fruitTransaction);
    }

    @Test(expected = NullPointerException.class)
    public void getHandlerByOperation_emptyStrategyMap_notOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        operationStrategy.getHandlerByOperation(fruitTransaction.getOperation())
                .handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void getHandlerByOperation_differentTypesOperationInTransactionAndStrategyMap_notOk() {
        strategyTest.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        operationStrategy.getHandlerByOperation(fruitTransaction.getOperation())
                .handle(fruitTransaction);
    }
}
