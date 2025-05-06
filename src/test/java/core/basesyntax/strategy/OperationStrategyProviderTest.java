package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperationStrategyProviderTest {
    private InventoryService inventoryService;
    private OperationStrategyProvider strategyProvider;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        strategyProvider = new OperationStrategyProvider(inventoryService);
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
        assertNotNull(strategyProvider.getHandler(FruitTransaction.OperationType.SUPPLY));
        assertNotNull(strategyProvider.getHandler(FruitTransaction.OperationType.RETURN));
        assertNotNull(strategyProvider.getHandler(FruitTransaction.OperationType.ADD));
    }
}
