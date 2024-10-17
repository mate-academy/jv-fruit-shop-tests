package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private Map<String, Integer> storage;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();

        OperationHandler supplyHandler = new SupplyOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    void shouldUpdateStorageCorrectly_whenMultipleOperationsAreProcessed() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50)
        );

        shopService.process(transactions);

        assertEquals(130, storage.get("apple"),
                "Apple quantity should be updated correctly after SUPPLY and PURCHASE");
        assertEquals(50, storage.get("banana"),
                "Banana quantity should be updated correctly after RETURN");
    }

    @Test
    void shouldKeepStorageEmpty_whenNoTransactionsAreProcessed() {
        List<FruitTransaction> transactions = List.of();
        shopService.process(transactions);

        assertTrue(transactions.isEmpty(),
                "Storage should remain empty after processing an empty list of transactions");
    }

    @Test
    void shouldUpdateStorageCorrectly_whenSingleSupplyTransactionIsProcessed() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 250));
        shopService.process(transactions);
        assertEquals(250, storage.get("banana"),
                "Storage should reflect the quantity after a single SUPPLY transaction");
    }
}
