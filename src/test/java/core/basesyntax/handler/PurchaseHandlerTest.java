package core.basesyntax.handler;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseHandler;
import core.basesyntax.service.handler.ReturnHandler;
import core.basesyntax.service.handler.SupplyHandler;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private static FruitShopService fruitShopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> fruitMap = new HashMap<>();
        fruitMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        fruitMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(fruitMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    void purchaseApple_Ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setFruit("apple");
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setQuantity(100);
        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setFruit("apple");
        transaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction2.setQuantity(70);
        List<FruitTransaction> transactions = List.of(transaction1, transaction2);
        fruitShopService.processOfOperations(transactions);
        boolean correct = Storage.fruits.get("apple").equals(30);
        Assertions.assertTrue(correct);
    }

    @Test
    void purchaseBanana_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setQuantity(30);
        List<FruitTransaction> transactions = List.of(transaction);
        assertThrows(RuntimeException.class,
                () -> fruitShopService.processOfOperations(transactions));
    }

    @Test
    void emptyStoragePurchase_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("plum");
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setQuantity(5);
        List<FruitTransaction> transactions = List.of(transaction);
        assertThrows(RuntimeException.class,
                () -> fruitShopService.processOfOperations(transactions));
    }
}
