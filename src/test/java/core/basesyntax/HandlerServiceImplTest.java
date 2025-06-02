package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandlerServiceImplTest {
    private HandlerServiceImpl handlerService;
    private OperationHandlers dummyHandler;

    @BeforeEach
    void setUp() {
        dummyHandler = transaction -> {
        };
        Map<FruitTransaction.Operation, OperationHandlers> handlers =
                new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, dummyHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, dummyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, dummyHandler);
        handlers.put(FruitTransaction.Operation.RETURN, dummyHandler);

        handlerService = new HandlerServiceImpl(handlers);
    }

    @Test
    void getHandler_shouldReturnCorrectHandler() {
        assertEquals(dummyHandler, handlerService.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_shouldReturnHandlerForKnownOperation() {
        OperationHandlers handler = handlerService.getHandler(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
        assertEquals(dummyHandler, handler);
    }

    @Test
    void getHandler_shouldReturnNullForUnknownOperation() {
        OperationHandlers handler = handlerService.getHandler(null);
        assertNull(handler);
    }

    @Test
    void getHandler_shouldReturnNullIfOperationNotMapped() {
        Map<FruitTransaction.Operation, OperationHandlers> handlers = Map.of(); // порожня мапа
        handlerService = new HandlerServiceImpl(handlers);

        OperationHandlers handler = handlerService.getHandler(FruitTransaction.Operation.BALANCE);
        assertNull(handler);
    }
}
