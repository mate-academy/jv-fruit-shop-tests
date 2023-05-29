package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utils.impl.BalanceOperation;
import core.basesyntax.utils.impl.PurchaseOperation;
import core.basesyntax.utils.impl.ReturnOperation;
import core.basesyntax.utils.impl.SupplyOperation;
import org.junit.Test;

public class StrategyTest {
    @Test
    public void strategy_validTransactions_ok() {
        Strategy strategy = new Strategy();
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
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
