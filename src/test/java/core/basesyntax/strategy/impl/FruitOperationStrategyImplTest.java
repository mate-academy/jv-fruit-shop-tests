package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.FruitOperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyImplTest {
    private static final int NORMAL_VALUE = 10;
    private static final int ZERO_VALUE = 0;
    private static final int LESS_THEN_VALUE = -5;
    private static final FruitTransaction NORMAL_VALUE_BALANCE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_BALANCE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_BALANCE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, LESS_THEN_VALUE);
    private static final FruitTransaction NORMAL_VALUE_SUPPLY_TRANSACTION =
            new FruitTransaction(Operation.SUPPLY, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_SUPPLY_TRANSACTION =
            new FruitTransaction(Operation.SUPPLY, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_SUPPLY_TRANSACTION =
            new FruitTransaction(Operation.SUPPLY, null, LESS_THEN_VALUE);
    private static final FruitTransaction NORMAL_VALUE_PURCHASE_TRANSACTION =
            new FruitTransaction(Operation.PURCHASE, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_PURCHASE_TRANSACTION =
            new FruitTransaction(Operation.PURCHASE, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_PURCHASE_TRANSACTION =
            new FruitTransaction(Operation.PURCHASE, null, LESS_THEN_VALUE);
    private static final FruitTransaction NORMAL_VALUE_RETURN_TRANSACTION =
            new FruitTransaction(Operation.RETURN, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_RETURN_TRANSACTION =
            new FruitTransaction(Operation.RETURN, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_RETURN_TRANSACTION =
            new FruitTransaction(Operation.RETURN, null, LESS_THEN_VALUE);
    private static final Map<Operation, OperationHandler> operationPicker =
            Map.of(Operation.BALANCE, new BalanceOperationHandlerImpl(),
                    Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                    Operation.RETURN, new ReturnOperationHandlerImpl(),
                    Operation.SUPPLY, new SupplyOperationHandlerImpl());
    private static FruitOperationStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitStrategy = new FruitOperationStrategyImpl(operationPicker);
    }

    @Test
    void strategyBalance_normalValue_Ok() {
        int expected = NORMAL_VALUE;
        int actual = fruitStrategy.countFruits(NORMAL_VALUE_BALANCE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyBalance_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = fruitStrategy.countFruits(ZERO_VALUE_BALANCE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyBalance_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStrategy.countFruits(LESS_THEN_ZERO_BALANCE_TRANSACTION));
    }

    @Test
    void strategySupply_normalValue_Ok() {
        int expected = NORMAL_VALUE;
        int actual = fruitStrategy.countFruits(NORMAL_VALUE_SUPPLY_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategySupply_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = fruitStrategy.countFruits(ZERO_VALUE_SUPPLY_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategySupply_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStrategy.countFruits(LESS_THEN_ZERO_SUPPLY_TRANSACTION));
    }

    @Test
    void strategyPurchase_normalValue_Ok() {
        int expected = -NORMAL_VALUE;
        int actual = fruitStrategy.countFruits(NORMAL_VALUE_PURCHASE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyPurchase_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = fruitStrategy.countFruits(ZERO_VALUE_PURCHASE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyPurchase_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStrategy.countFruits(LESS_THEN_ZERO_PURCHASE_TRANSACTION));
    }

    @Test
    void strategyReturn_normalValue_Ok() {
        int expected = NORMAL_VALUE;
        int actual = fruitStrategy.countFruits(NORMAL_VALUE_RETURN_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyReturn_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = fruitStrategy.countFruits(ZERO_VALUE_RETURN_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void strategyReturn_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitStrategy.countFruits(LESS_THEN_ZERO_RETURN_TRANSACTION));
    }
}
