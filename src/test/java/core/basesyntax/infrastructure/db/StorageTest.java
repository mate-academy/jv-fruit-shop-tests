package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void dataStoredOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 105);
        assertEquals(Storage.STORAGE, expected);
    }
}
