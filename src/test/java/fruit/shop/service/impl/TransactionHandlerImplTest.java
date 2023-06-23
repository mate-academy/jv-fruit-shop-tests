package fruit.shop.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import fruit.shop.db.Storage;
import fruit.shop.model.FruitTransaction;
import fruit.shop.service.strategy.OperationHandler;
import fruit.shop.service.strategy.OperationStrategy;
import fruit.shop.service.strategy.impl.BalanceHandler;
import fruit.shop.service.strategy.impl.OperationStrategyImpl;
import fruit.shop.service.strategy.impl.PurchaseHandler;
import fruit.shop.service.strategy.impl.ReturnHandler;
import fruit.shop.service.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionHandlerImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> fruitMap = new HashMap<>();
        fruitMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        fruitMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        fruitMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        strategy = new OperationStrategyImpl(fruitMap);
    }

    @AfterEach
    void clearStorage() {
        Storage.FRUITS.clear();
    }

    @Test
    void parseStorage_balanceBanana_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setValue(200);
        List<FruitTransaction> transactions = List.of(firstTransaction);
        new TransactionHandlerImpl(strategy).parseStorage(transactions);
        boolean correct = Storage.FRUITS.get("banana").equals(200);
        assertTrue(correct);
    }

    @Test
    void parseStorage_returnBanana_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setValue(200);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit("banana");
        secondTransaction.setOperation(FruitTransaction.Operation.RETURN);
        secondTransaction.setValue(30);

        List<FruitTransaction> transactions = List.of(firstTransaction, secondTransaction);
        new TransactionHandlerImpl(strategy).parseStorage(transactions);
        boolean correct = Storage.FRUITS.get("banana").equals(230);
        assertTrue(correct);
    }

    @Test
    void parseStorage_supplyBanana_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        firstTransaction.setValue(100);
        List<FruitTransaction> transactions = List.of(firstTransaction);
        new TransactionHandlerImpl(strategy).parseStorage(transactions);
        boolean correct = Storage.FRUITS.get("banana").equals(100);
        assertTrue(correct);
    }

    @Test
    void parseStorage_purchaseBanana_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setValue(200);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit("banana");
        secondTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        secondTransaction.setValue(30);

        List<FruitTransaction> transactions = List.of(firstTransaction, secondTransaction);
        new TransactionHandlerImpl(strategy).parseStorage(transactions);
        boolean correct = Storage.FRUITS.get("banana").equals(170);
        assertTrue(correct);
    }

    @Test
    void parseStorage_emptyStoragePurchase_NotOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("cherry");
        firstTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        firstTransaction.setValue(5);
        List<FruitTransaction> transactions = List.of(firstTransaction);
        assertThrows(RuntimeException.class,
                () -> new TransactionHandlerImpl(strategy).parseStorage(transactions));
    }

    @Test
    void parseStorage_nullTransaction_NotOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setValue(200);
        FruitTransaction secondTransaction = new FruitTransaction();
        List<FruitTransaction> transactions = List.of(firstTransaction, secondTransaction);
        assertThrows(RuntimeException.class,
                () -> new TransactionHandlerImpl(strategy).parseStorage(transactions));
    }
}
