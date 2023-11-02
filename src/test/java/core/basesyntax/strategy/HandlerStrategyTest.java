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
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class HandlerStrategyTest {
    private static InventoryDao inventoryDao;
    private static final Map<OperationType, OperationHandler> testMap = new HashMap<>();
    private static HandlerStrategy handlerStrategy = new HandlerStrategy(testMap);

    private static BalanceHandler balanceHandler;
    private static PurchaseHandler purchaseHandler;
    private static ReturnHandler returnHandler;
    private static SupplyHandler supplyHandler;

    @BeforeAll
    public static void setUP() {
        handlerStrategy = new HandlerStrategy(testMap);
        inventoryDao = new InventoryDaoImpl();
        balanceHandler = new BalanceHandler(inventoryDao);
        purchaseHandler = new PurchaseHandler(inventoryDao);
        returnHandler = new ReturnHandler(inventoryDao);
        supplyHandler = new SupplyHandler(inventoryDao);
        testMap.put(OperationType.BALANCE, balanceHandler);
        testMap.put(OperationType.PURCHASE, purchaseHandler);
        testMap.put(OperationType.RETURN, returnHandler);
        testMap.put(OperationType.SUPPLY, supplyHandler);
    }

    @Test
    public void etHandlerByOperationType_validTypes_Ok() {
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
    public void getHandlerByOperationType_byNullOperationType_NotOk() {
        assertThrows(RuntimeException.class, () -> handlerStrategy.getHandlerByOperationType(null));
    }

    @Test
    public void getStrategyMap_allValidConditions_Ok() {
        assertEquals(handlerStrategy.getStrategyMap(), testMap);
    }
}
