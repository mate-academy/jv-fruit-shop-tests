package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessorServiceTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");
    private static int initialBananaBalance;
    private static int initialAppleBalance;

    @BeforeAll
    static void beforeAll() {
        Storage.storage.put(banana,15);
        Storage.storage.put(apple,35);
        initialBananaBalance = Storage.storage.get(banana);
        initialAppleBalance = Storage.storage.get(apple);
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
    }

    @Test
    void processValidTransactions() {
        int quantity = 25;
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setFruit(banana);
        transaction1.setQuantity(quantity);
        transaction1.setOperation(FruitTransaction.Operation.SUPPLY);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setFruit(apple);
        transaction2.setQuantity(quantity);
        transaction2.setOperation(FruitTransaction.Operation.PURCHASE);

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);

        OperationProcessorService operationProcessorService = new OperationProcessorService();

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        operationProcessorService.process(transactions, operationStrategy);

        int expectedBananaBalance = initialBananaBalance + quantity;

        int expectedAppleBalance = initialAppleBalance - quantity;

        assertEquals(expectedBananaBalance, Storage.storage.get(banana));
        assertEquals(expectedAppleBalance, Storage.storage.get(apple));
    }
}
