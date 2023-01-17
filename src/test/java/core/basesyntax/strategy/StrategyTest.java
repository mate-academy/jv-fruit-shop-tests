package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.BalanceOperation;
import core.basesyntax.utils.impl.PurchaseOperation;
import core.basesyntax.utils.impl.ReturnOperation;
import core.basesyntax.utils.impl.SupplyOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        strategy = new Strategy();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
    }

    @Test
    public void strategy_validTransactions_ok() {
        assertEquals("hello", strategy
                .getCalculateOperation(fruitTransaction).getClass(), BalanceOperation.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals("hello", strategy
                .getCalculateOperation(fruitTransaction).getClass(), PurchaseOperation.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        assertEquals("hello", strategy
                .getCalculateOperation(fruitTransaction).getClass(), ReturnOperation.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals("hello", strategy
                .getCalculateOperation(fruitTransaction).getClass(), SupplyOperation.class);
    }
}
