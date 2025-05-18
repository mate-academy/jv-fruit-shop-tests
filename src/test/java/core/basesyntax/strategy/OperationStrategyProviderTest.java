package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.InventoryService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyProviderTest {
    private InventoryService inventoryService;
    private OperationStrategyProvider strategyProvider;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        strategyProvider = new OperationStrategyProvider(
                Map.of(
                        FruitTransaction.OperationType.SUPPLY, new SupplyOperationHandler(),
                        FruitTransaction.OperationType.RETURN, new ReturnOperationHandler(),
                        FruitTransaction.OperationType.ADD,
                        new BalanceOperationHandler()
                )
        );
    }

    @Test
    void getHandler_supplyOperation_returnsSupplyHandler() {
        OperationHandler handler = strategyProvider
                .getHandler(FruitTransaction.OperationType.SUPPLY);
        assertNotNull(handler, "Handler for SUPPLY should not be null");
    }

    @Test
    void getHandler_returnOperation_returnsReturnHandler() {
        OperationHandler handler = strategyProvider
                .getHandler(FruitTransaction.OperationType.RETURN);
        assertNotNull(handler, "Handler for RETURN should not be null");
    }

    @Test
    void getHandler_addOperation_returnsAddHandler() {
        OperationHandler handler = strategyProvider
                .getHandler(FruitTransaction.OperationType.ADD);
        assertNotNull(handler, "Handler for ADD should not be null");
    }

    @Test
    void getHandler_shouldReturnCorrectHandlerForEachOperation() {
        assertEquals(SupplyOperationHandler.class,
                strategyProvider.getHandler(FruitTransaction.OperationType.SUPPLY).getClass());
        assertEquals(ReturnOperationHandler.class,
                strategyProvider.getHandler(FruitTransaction.OperationType.RETURN).getClass());
        assertEquals(BalanceOperationHandler.class,
                strategyProvider.getHandler(FruitTransaction.OperationType.ADD).getClass());
    }

}
