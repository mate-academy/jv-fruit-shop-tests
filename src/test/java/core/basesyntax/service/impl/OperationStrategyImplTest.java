package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final String FRUIT = "pear";
    private static final int QUANTITY = 10;
    private static Map<Operation, OperationHandler> handlers;
    private static OperationStrategy operationStrategy;
    private FruitTransfer fruitTransfer;

    @BeforeAll
    static void beforeAll() {
        handlers = Map.of(Operation.BALANCE, new BalanceHandler(),
                Operation.PURCHASE, new PurchaseHandler(),
                Operation.RETURN, new ReturnHandler(),
                Operation.SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getStrategy_checkAllOperationStrategies_Ok() {
        fruitTransfer = new FruitTransfer(Operation.BALANCE, FRUIT, QUANTITY);
        OperationHandler expectedBalance = handlers.get(Operation.BALANCE);
        OperationHandler actualBalance = operationStrategy.getStrategy(fruitTransfer);
        assertEquals(expectedBalance, actualBalance);
        fruitTransfer = new FruitTransfer(Operation.PURCHASE, FRUIT, QUANTITY);
        OperationHandler expectedPurchase = handlers.get(Operation.PURCHASE);
        OperationHandler actualPurchase = operationStrategy.getStrategy(fruitTransfer);
        assertEquals(expectedPurchase, actualPurchase);
        fruitTransfer = new FruitTransfer(Operation.SUPPLY, FRUIT, QUANTITY);
        OperationHandler expectedSupply = handlers.get(Operation.SUPPLY);
        OperationHandler actualSupply = operationStrategy.getStrategy(fruitTransfer);
        assertEquals(expectedSupply, actualSupply);
        fruitTransfer = new FruitTransfer(Operation.RETURN, FRUIT, QUANTITY);
        OperationHandler expectedReturn = handlers.get(Operation.RETURN);
        OperationHandler actualReturn = operationStrategy.getStrategy(fruitTransfer);
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    void getStrategy_nullValueArgument_NotOK() {
        fruitTransfer = null;
        assertThrows(InvalidDataException.class, () ->
                operationStrategy.getStrategy(fruitTransfer));
    }
}
