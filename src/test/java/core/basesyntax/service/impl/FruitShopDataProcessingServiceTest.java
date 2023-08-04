package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopDataProcessingServiceTest {
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final Map<Operation, OperationHandler> operationHandlerMap = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler());
    private OperationStrategy operationStrategy;
    private DataProcessingService dataProcessingService;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        dataProcessingService = new FruitShopDataProcessingService(operationStrategy);
    }

    @Test
    void processData_validFruitTransactions_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(DEFAULT_FRUIT_NAME, 20, Operation.BALANCE));
        fruitTransactions.add(new FruitTransaction(DEFAULT_FRUIT_NAME, 100, Operation.SUPPLY));
        fruitTransactions.add(new FruitTransaction(DEFAULT_FRUIT_NAME, 13, Operation.PURCHASE));
        fruitTransactions.add(new FruitTransaction(DEFAULT_FRUIT_NAME, 5, Operation.PURCHASE));
        fruitTransactions.add(new FruitTransaction(DEFAULT_FRUIT_NAME, 50, Operation.SUPPLY));
        dataProcessingService.processData(fruitTransactions);
        int expected = 152;
        int actual = Storage.storage.get(DEFAULT_FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
