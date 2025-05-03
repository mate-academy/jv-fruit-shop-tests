package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private TransactionService transactionService;
    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandleMap;

    @Before
    public void setUp() {
        operationHandleMap = new HashMap<>();
        operationHandleMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandleMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandleMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandleMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandleMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
        Storage.fruits.clear();
    }

    @Test
    public void procces_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction("orange", 25, Operation.BALANCE));
        transactions.add(new FruitTransaction("melon", 25, Operation.RETURN));
        transactions.add(new FruitTransaction("apple", 25, Operation.SUPPLY));
        transactions.add(new FruitTransaction("orange", 15, Operation.PURCHASE));
        transactions.add(new FruitTransaction("melon", 15, Operation.PURCHASE));
        transactions.add(new FruitTransaction("apple", 15, Operation.PURCHASE));
        transactionService.process(transactions);
        Integer expectedQuantity = 10;
        Integer actualQuantity = Storage.fruits.get("orange");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
        actualQuantity = Storage.fruits.get("melon");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
        actualQuantity = Storage.fruits.get("apple");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
    }
}
