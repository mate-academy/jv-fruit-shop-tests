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
    }

    @Test
    void get_returnBalanceHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_returnSupplyHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.SUPPLY, FRUIT, QUANTITY);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_returnPurchaseHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }

    @Test
    void get_repayReturnHandlerImpl_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN, FRUIT, QUANTITY);
        FruitHandler fruitHandler = fruitHandlerStrategy.get(fruitTransaction);
        assertEquals(fruitHandlerStrategy.get(fruitTransaction), fruitHandler);
        assertDoesNotThrow(() -> fruitHandlerStrategy.get(fruitTransaction));
    }
}
