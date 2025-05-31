package core.basesyntax.newtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandlerServiceImplTest {
    private HandlerServiceImpl handlerService;
    private OperationHandlers dummyHandler;

    @BeforeEach
    public void setUp() {
        dummyHandler = transaction -> {};
        Map<FruitTransaction.Operation, OperationHandlers> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, dummyHandler);
        map.put(FruitTransaction.Operation.SUPPLY, dummyHandler);
        map.put(FruitTransaction.Operation.PURCHASE, dummyHandler);
        map.put(FruitTransaction.Operation.RETURN, dummyHandler);
        handlerService = new HandlerServiceImpl(map);
    }

    @Test
    public void getHandler_returnsCorrectHandler() {
        assertEquals(dummyHandler, handlerService.getHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(dummyHandler, handlerService.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    public void getHandler_returnsNullForUnknownOperation() {
        assertNull(handlerService.getHandler(null));
    }
}
