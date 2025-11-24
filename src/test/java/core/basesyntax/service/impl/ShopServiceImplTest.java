package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopServiceImpl;

    @Test
    void process_transactionsNull_notOk() {
        shopServiceImpl = new ShopServiceImpl(null, null);
        assertThrows(IllegalArgumentException.class, () -> shopServiceImpl.process(null));
    }

    @Test
    void process_executesHandlerLogic_ok() {
        class HandlerSpy implements OperationHandler {
            private boolean wasCalled = false;

            @Override
            public void handle(FruitTransaction transaction, ShopStorage storage) {
                wasCalled = true;
            }
        }

        HandlerSpy handlerSpy = new HandlerSpy();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, handlerSpy);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopServiceImpl = new ShopServiceImpl(operationStrategy, null);

        FruitTransaction transaction = FruitTransaction.of(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        shopServiceImpl.process(List.of(transaction));

        assertTrue(handlerSpy.wasCalled, "The strategy should have invoked the handler");
    }
}
