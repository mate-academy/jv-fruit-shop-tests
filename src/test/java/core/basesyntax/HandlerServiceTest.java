package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandlerServiceTest {
    private OperationHandlers dummyHandler;
    private HandlerServiceImpl handlerService;

    @BeforeEach
    void setUp() {
        dummyHandler = transaction -> {}; // no-op
        Map<FruitTransaction.Operation, OperationHandlers> handlers =
                new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, dummyHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, dummyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, dummyHandler);
        handlers.put(FruitTransaction.Operation.RETURN, dummyHandler);

        handlerService = new HandlerServiceImpl(handlers);
    }

    @Test
    void getHandler_shouldReturnCorrectHandlerForEachOperation() {
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            OperationHandlers handler = handlerService.getHandler(op);
            assertNotNull(handler, "Handler for " + op + " should not be null");
            assertEquals(dummyHandler, handler, "Handler should match dummyHandler for " + op);
        }
    }

    @Test
    void getHandler_shouldReturnNullIfHandlerNotPresent() {
        Map<FruitTransaction.Operation, OperationHandlers> incompleteMap =
                new EnumMap<>(FruitTransaction.Operation.class);
        incompleteMap.put(FruitTransaction.Operation.BALANCE, dummyHandler);
        HandlerServiceImpl incompleteService = new HandlerServiceImpl(incompleteMap);

        assertNull(incompleteService.getHandler(FruitTransaction.Operation.SUPPLY),
                "Expected null for missing SUPPLY handler");
    }

    @Test
    void constructor_shouldNotThrowExceptionIfHandlersMapIsNull() {
        assertDoesNotThrow(() -> {
            new HandlerServiceImpl(null);
        }, "Constructor should accept null handlers map without throwing exception");
    }

    @Test
    void constructor_shouldCreateInstance_whenHandlersMapIsNotNull() {
        Map<FruitTransaction.Operation, OperationHandlers> handlers =
                new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, dummyHandler);

        HandlerServiceImpl service = new HandlerServiceImpl(handlers);

        assertNotNull(service, "HandlerServiceImpl instance should be created");
        assertEquals(dummyHandler, service.getHandler(FruitTransaction.Operation.BALANCE),
                "Handler should be correctly returned from the service");
    }

    @Test
    void getHandler_shouldReturnNull_whenEmptyHandlersMap() {
        HandlerServiceImpl emptyService =
                new HandlerServiceImpl(new EnumMap<>(FruitTransaction.Operation.class));
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            assertNull(emptyService.getHandler(op), "Expected null for operation "
                    + op + " when map is empty");
        }
    }

    @Test
    void constructor_shouldStoreHandlersMapAsReference() {
        Map<FruitTransaction.Operation, OperationHandlers> map =
                new EnumMap<>(FruitTransaction.Operation.class);
        HandlerServiceImpl service = new HandlerServiceImpl(map);
        OperationHandlers handler = tx -> {};
        map.put(FruitTransaction.Operation.SUPPLY, handler);

        assertEquals(handler, service.getHandler(FruitTransaction.Operation.SUPPLY),
                "Handler map should be stored by reference, not copied");
    }

    @Test
    void getHandler_shouldBehaveCorrectly_afterHandlerMapModifiedExternally() {
        Map<FruitTransaction.Operation, OperationHandlers> map =
                new EnumMap<>(FruitTransaction.Operation.class);
        HandlerServiceImpl service = new HandlerServiceImpl(map);

        assertNull(service.getHandler(FruitTransaction.Operation.PURCHASE),
                "Expected null before external addition");

        OperationHandlers externalHandler = tx -> {};
        map.put(FruitTransaction.Operation.PURCHASE, externalHandler);

        assertEquals(externalHandler, service.getHandler(FruitTransaction.Operation.PURCHASE),
                "Expected updated handler after external map modification");
    }

    @Test
    void getHandler_shouldReturnDifferentHandlers_ifMappedDifferently() {
        OperationHandlers handler1 = tx -> {};
        OperationHandlers handler2 = tx -> {};
        Map<FruitTransaction.Operation, OperationHandlers> map =
                new EnumMap<>(FruitTransaction.Operation.class);
        map.put(FruitTransaction.Operation.SUPPLY, handler1);
        map.put(FruitTransaction.Operation.RETURN, handler2);

        HandlerServiceImpl service = new HandlerServiceImpl(map);

        assertEquals(handler1, service.getHandler(FruitTransaction.Operation.SUPPLY));
        assertEquals(handler2, service.getHandler(FruitTransaction.Operation.RETURN));
        assertNotEquals(handler1, handler2, "Handlers should be different");
    }
}
