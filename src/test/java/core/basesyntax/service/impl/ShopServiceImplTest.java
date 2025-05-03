package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy operationStrategy;
    private ShopImpl shop;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
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

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setFruit("apple");
        transaction3.setQuantity(5);

        FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setOperation(FruitTransaction.Operation.RETURN);
        transaction4.setFruit("apple");
        transaction4.setQuantity(5);

        shop.process(List.of(transaction1, transaction2, transaction3, transaction4));

        int finalQuantity = core.basesyntax.db.Storage.getFruitQuantity("apple");
        assertEquals(15, finalQuantity);
    }

    @AfterEach
    void clearStorage() {
        Storage.clearStorage();
    }
}
