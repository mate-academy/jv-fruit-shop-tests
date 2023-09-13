package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationProcessImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationProcessTest {
    private static OperationProcess operationProcess;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap
                = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        OperationHandlerStrategy handlerStrategy = new OperationHandlerStrategyImpl(handlerMap);
        operationProcess = new OperationProcessImpl(handlerStrategy);
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.clear();
    }

    @Test
    void processOperation_validData_Ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 30));
        operationProcess.processOperation(fruitTransactions);
        assertEquals(2, Storage.STORAGE.size());
        assertEquals(50, Storage.STORAGE.get("banana"));
        assertEquals(80, Storage.STORAGE.get("apple"));
    }
}
