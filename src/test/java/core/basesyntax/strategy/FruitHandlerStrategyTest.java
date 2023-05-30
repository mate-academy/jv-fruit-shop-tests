package core.basesyntax.strategy;

import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceFruitHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseFruitHandlerImpl;
import core.basesyntax.strategy.impl.ReturnFruitHandlerImpl;
import core.basesyntax.strategy.impl.SupplyFruitHandlerImpl;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitHandlerStrategyTest {
    private static FruitHandlerStrategy fruitHandlerStrategy;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void setUp() {
        fruitHandlerStrategy = new FruitHandlerStrategy((new HashMap<>() {
            {
                put(Operation.BALANCE, new BalanceFruitHandlerImpl());
                put(Operation.SUPPLY, new SupplyFruitHandlerImpl());
                put(Operation.PURCHASE, new PurchaseFruitHandlerImpl());
                put(Operation.RETURN, new ReturnFruitHandlerImpl());
            }
        }));
        fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 20);
    }

    @Test
    void fruitHandlerStrategy_returnBalanceHandlerImpl_ok() {
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        Assertions.assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        Assertions.assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void fruitHandlerStrategy_returnSupplyHandlerImpl_ok() {
        fruitTransaction.setOperation(Operation.SUPPLY);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        Assertions.assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        Assertions.assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void fruitHandlerStrategy_returnPurchaseHandlerImpl_ok() {
        fruitTransaction.setOperation(Operation.PURCHASE);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        Assertions.assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        Assertions.assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void fruitHandlerStrategy_repayReturnHandlerImpl_ok() {
        fruitTransaction.setOperation(Operation.RETURN);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        Assertions.assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        Assertions.assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }
}
