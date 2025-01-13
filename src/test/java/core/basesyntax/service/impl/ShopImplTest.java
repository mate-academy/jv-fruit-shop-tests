package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopImplTest {
    private OperationStrategy operationStrategy;
    private ShopImpl shop;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(handlers);
        shop = new ShopImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_updatesStorageCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("apple");
        transaction1.setQuantity(10);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setFruit("apple");
        transaction2.setQuantity(5);

        shop.process(List.of(transaction1, transaction2));

        int finalQuantity = core.basesyntax.db.Storage.getFruitQuantity("apple");
        assertEquals(15, finalQuantity);
    }

    @Test
    void process_emptyTransactionList_doesNotModifyStorage() {
        shop.process(List.of());

        Map<String, Integer> storageContents = core.basesyntax.db.Storage.getAllFruits();
        assertTrue(storageContents.isEmpty());
    }
}
