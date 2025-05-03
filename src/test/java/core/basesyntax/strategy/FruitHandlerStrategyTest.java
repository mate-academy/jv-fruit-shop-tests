package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceFruitHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseFruitHandlerImpl;
import core.basesyntax.strategy.impl.ReturnFruitHandlerImpl;
import core.basesyntax.strategy.impl.SupplyFruitHandlerImpl;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitHandlerStrategyTest {
    private static FruitHandlerStrategy fruitHandlerStrategy;
    private static final int QUANTITY = 50;
    private static final String FRUIT = "apple";
    private static final FruitHandler balanceHandler = new BalanceFruitHandlerImpl();
    private static final FruitHandler supplyHandler = new SupplyFruitHandlerImpl();
    private static final FruitHandler purchaseHandler = new PurchaseFruitHandlerImpl();
    private static final FruitHandler returnHandler = new ReturnFruitHandlerImpl();

    @BeforeAll
    static void setUp() {
        fruitHandlerStrategy = new FruitHandlerStrategy((new HashMap<>() {
            {
                put(Operation.BALANCE, balanceHandler);
                put(Operation.SUPPLY, supplyHandler);
                put(Operation.PURCHASE, purchaseHandler);
                put(Operation.RETURN, returnHandler);
            }
        }));
    }

    @Test
    void get_returnBalanceHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY);
        assertEquals(balanceHandler, fruitHandlerStrategy.get(fruitTransaction));
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_returnSupplyHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.SUPPLY, FRUIT, QUANTITY);
        assertEquals(supplyHandler, fruitHandlerStrategy.get(fruitTransaction));
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_returnPurchaseHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        assertEquals(purchaseHandler, fruitHandlerStrategy.get(fruitTransaction));
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_repayReturnHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN, FRUIT, QUANTITY);
        assertEquals(returnHandler, fruitHandlerStrategy.get(fruitTransaction));
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }
}
