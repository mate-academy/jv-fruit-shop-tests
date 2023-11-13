package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationProcess;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 10;
    private static final int BANANA_QUANTITY = 20;
    private static OperationProcess operationProcess;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategy(handlers);
        operationProcess = new OperationProcessImpl(operationStrategy);
    }

    @Test
    void processTransaction_callsCorrectOperationHandlers() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruit(APPLE);
        balanceTransaction.setQuantity(APPLE_QUANTITY);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(Operation.SUPPLY);
        supplyTransaction.setFruit(BANANA);
        supplyTransaction.setQuantity(BANANA_QUANTITY);
        operationProcess.processTransaction(List.of(balanceTransaction, supplyTransaction));
        assertEquals(APPLE_QUANTITY, Storage.SHOPSTORAGE.get(APPLE));
        assertEquals(BANANA_QUANTITY, Storage.SHOPSTORAGE.get(BANANA));
    }
}
