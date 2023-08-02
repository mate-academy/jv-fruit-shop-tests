package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.MapBuilderOperationService;
import core.basesyntax.service.implementations.MapBuilderOperationServiceImpl;
import core.basesyntax.strategy.handler.DataHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapBuilderOperationServiceTest {
    private static final int OPERATION_NUMBER = 4;
    private MapBuilderOperationService mapBuilderOperationService;

    @BeforeEach
    void setUp() {
        mapBuilderOperationService = new MapBuilderOperationServiceImpl();
    }

    @Test
    void testInitialization() {
        Map<FruitTransaction.Operation, DataHandler> handlerMap
                = mapBuilderOperationService.initializeMap();
        assertEquals(OPERATION_NUMBER, handlerMap.size());
        assertTrue(handlerMap.containsKey(FruitTransaction.Operation.BALANCE));
        assertTrue(handlerMap.containsKey(FruitTransaction.Operation.SUPPLY));
        assertTrue(handlerMap.containsKey(FruitTransaction.Operation.PURCHASE));
        assertTrue(handlerMap.containsKey(FruitTransaction.Operation.RETURN));
    }
}
