package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitstorge.FruitStorage;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.operations.Operation;
import core.basesyntax.strategy.BalanceOperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationStrategy;
import core.basesyntax.strategy.SupplyOperationStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {

    @Test
    public void testHandleTransaction() {

        OperationHandler addHandler = new BalanceOperationStrategy();
        OperationHandler purchaseHandler = new PurchaseOperationStrategy();
        OperationHandler subtractHandler = new SupplyOperationStrategy();
        // Create a map with stub handlers
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, addHandler);
        operationHandlerMap.put(Operation.PURCHASE, purchaseHandler);
        operationHandlerMap.put(Operation.SUPPLY, subtractHandler);

        TransactionServiceImpl transactionService = new TransactionServiceImpl(operationHandlerMap);

        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.SUPPLY, "Apple",5),
                new FruitTransaction(Operation.SUPPLY, "Apple",5),
                new FruitTransaction(Operation.SUPPLY, "Banana",10),
                new FruitTransaction(Operation.BALANCE, "Apple",5),
                new FruitTransaction(Operation.PURCHASE, "Banana",3)
        );
        // Call the handleTransaction method
        transactionService.handleTransaction(transactions);

        assertEquals(15, FruitStorage.fruitStorage.get("Apple"));
        assertEquals(7, FruitStorage.fruitStorage.get("Banana"));
    }
}
