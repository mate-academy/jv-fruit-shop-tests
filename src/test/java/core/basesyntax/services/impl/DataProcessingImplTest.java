package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.operationhandlers.ReturnOperationHandler;
import core.basesyntax.operationhandlers.SupplyOperationHandler;
import core.basesyntax.services.DataProcessing;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessingImplTest {
    private DataProcessing dataProcessing;
    private Storage storage = new Storage();
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationOperationHandlerMap = new HashMap<>();
        operationOperationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationOperationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationOperationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationOperationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);
        dataProcessing = new DataProcessingImpl((OperationStrategyImpl) operationStrategy, storage);
    }

    @Test
    void dataProcessing_validFruitTransactionObjects_ok() {
        List<String> strings = new ArrayList<>();
        strings.add("b,banana,20");
        strings.add("b,apple,100");
        strings.add("s,banana,100");

        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        FruitTransaction fruitTransaction1 = new FruitTransaction();

        fruitTransaction1.setQuantity(120);
        fruitTransaction1.setFruit("banana");
        fruitTransactions.add(fruitTransaction1);

        FruitTransaction fruitTransaction2 = new FruitTransaction();

        fruitTransaction2.setQuantity(100);
        fruitTransaction2.setFruit("apple");
        fruitTransactions.add(fruitTransaction2);

        List<FruitTransaction> fruitTransactions1 = dataProcessing.processData(strings);

        Assertions.assertEquals(2, fruitTransactions1.size(),
                "The number of processed transactions does not match the expected.");

        Assertions.assertEquals(fruitTransactions.get(0).getFruit(),
                fruitTransactions1.get(0).getFruit());
        Assertions.assertEquals(fruitTransactions.get(1).getQuantity(),
                fruitTransactions1.get(1).getQuantity());
    }
}
