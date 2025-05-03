package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        OperationHandler balanceHandler = new BalanceOperation();
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(List.of(transaction));
        Assert.assertEquals("apple", 50);
    }

    @Test
    void process_emptyTransactions_noHandlersCalled() {
        List<FruitTransaction> emptyTransactions = Collections.emptyList();
        shopService.process(emptyTransactions);
        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        Assert.assertTrue("Inventory should remain empty "
                        + "when processing an empty transaction list",
                inventory.isEmpty()
        );
    }
}
