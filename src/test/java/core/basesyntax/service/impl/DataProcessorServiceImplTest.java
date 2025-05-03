package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.impl.BalanceOperationHandler;
import core.basesyntax.operations.impl.PurchaseOperationHandler;
import core.basesyntax.operations.impl.ReturnOperationHandler;
import core.basesyntax.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.service.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorServiceImplTest {
    private static Storage storage = new Storage();
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        Storage.clear();
        Map<Operation, OperationHandler> operationHandlerMap =
                new HashMap<>();

        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void process() {
        List<FruitTransaction> inputData = new ArrayList<>();
        inputData.add(new FruitTransaction("apple", 10, Operation.SUPPLY));
        inputData.add(new FruitTransaction("banana", 10, Operation.SUPPLY));
        inputData.add(new FruitTransaction("orange", 10, Operation.SUPPLY));

        DataProcessorService dataProcessorService = new DataProcessorServiceImpl(operationStrategy);
        dataProcessorService.process(inputData);

        for (FruitTransaction transaction : inputData) {
            String fruit = transaction.getFruitName();
            Assertions.assertEquals(transaction.getQuantity(), Storage.getFruitBalance(fruit));
        }

    }
}
