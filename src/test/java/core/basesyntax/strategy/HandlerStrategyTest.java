package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.InventoryDao;
import core.basesyntax.dao.InventoryDaoImpl;
import core.basesyntax.model.FruitTransaction.OperationType;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HandlerStrategyTest {
    private static InventoryDao inventoryDao;
    private static Map<OperationType, OperationHandler> strategyMap;
    private static HandlerStrategy handlerStrategy;
    private static BalanceHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        strategyMap = new HashMap<>();
        handlerStrategy = new HandlerStrategy(strategyMap);
        inventoryDao = new InventoryDaoImpl();

        balanceHandler = new BalanceHandler(inventoryDao);
        purchaseHandler = new PurchaseHandler(inventoryDao);
        returnHandler = new ReturnHandler(inventoryDao);
        supplyHandler = new SupplyHandler(inventoryDao);

        strategyMap.put(OperationType.BALANCE, balanceHandler);
        strategyMap.put(OperationType.PURCHASE, purchaseHandler);
        strategyMap.put(OperationType.RETURN, returnHandler);
        strategyMap.put(OperationType.SUPPLY, supplyHandler);
    }

    @Test
    void getHandlerByOperationType_validTypes_Ok() {
        assertAll(
                () -> assertEquals(balanceHandler,
                        handlerStrategy.getHandlerByOperationType(OperationType.BALANCE),
                        "It should be BalanceHandler object for OperationType.BALANCE"),
                () -> assertEquals(purchaseHandler,
                        handlerStrategy.getHandlerByOperationType(OperationType.PURCHASE),
                        "It should be PurchaseHandler object for OperationType.PURCHASE"),
                () -> assertEquals(returnHandler,
                        handlerStrategy.getHandlerByOperationType(OperationType.RETURN),
                        "It should be ReturnHandler object for OperationType.RETURN"),
                () -> assertEquals(supplyHandler,
                        handlerStrategy.getHandlerByOperationType(OperationType.SUPPLY),
                        "It should be SupplyHandler object for OperationType.SUPPLY")
        );
    }

    @Test
    void getHandlerByOperationType_byNullOperationType_NotOk() {
        assertThrows(RuntimeException.class,
                () -> handlerStrategy.getHandlerByOperationType(null));
    }

    @Test
    void getStrategyMap_allValidConditions_Ok() {
        assertEquals(handlerStrategy.getStrategyMap(), strategyMap);
    }
}
