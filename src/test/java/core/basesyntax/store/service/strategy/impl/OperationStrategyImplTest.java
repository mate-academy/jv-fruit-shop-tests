package core.basesyntax.store.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.store.handler.OperationHandler;
import core.basesyntax.store.handler.impl.BalanceOperation;
import core.basesyntax.store.handler.impl.PurchaseOperation;
import core.basesyntax.store.handler.impl.ReturnOperation;
import core.basesyntax.store.handler.impl.SupplyOperation;
import core.basesyntax.store.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        // Створюємо обробники операцій
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler supplyHandler = new SupplyOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();

        // Створюємо мапу обробників для різних операцій
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlers.put(FruitTransaction.Operation.RETURN, returnHandler);

        // Ініціалізуємо стратегію з цією мапою
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperation_returnCorrectHandler() {
        // Перевірка, чи повертається правильний обробник для кожної операції
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertTrue(handler instanceof BalanceOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(handler instanceof SupplyOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertTrue(handler instanceof ReturnOperation);
    }

    @Test
    void getHandler_invalidOperation_returnNull() {
        // Тест на наявність коректної обробки випадку, коли операція не знайдена в мапі
        OperationHandler handler = operationStrategy.getHandler(null);
        assertNull(handler, "Handler for null operation should be null");
    }
}
