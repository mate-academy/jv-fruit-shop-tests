package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.OperationStrategy;
import core.basesyntax.ShopService;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private Storage storage;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storage));
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_shouldApplyAllOperations() {
        FruitTransaction balance = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 50);
        FruitTransaction supply = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "apple", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(balance);
        transactions.add(supply);
        shopService.process(transactions);
        assertEquals(70, storage.getFruits().get("apple"));
    }
}
