package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static FruitOperationStrategyImpl fruitOperationStrategy;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void setUp() {
        Storage.fruits.put(BANANA, 50);
        Storage.fruits.put(APPLE, 50);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_BalanceOperation_Ok() {
        fruitOperationStrategy = new FruitOperationStrategyImpl(Map.of(FruitOperation.BALANCE,
                new BalanceOperationHandler(storageDao)));
        FruitTransaction fruitTransactionBanana = new FruitTransaction(FruitOperation.BALANCE,
                BANANA, 100);
        FruitTransaction fruitTransactionApple = new FruitTransaction(FruitOperation.BALANCE,
                APPLE, 50);
        fruitOperationStrategy.getHandler(FruitOperation.BALANCE).apply(fruitTransactionApple);
        fruitOperationStrategy.getHandler(FruitOperation.BALANCE).apply(fruitTransactionBanana);
        int expectedBanana = 100;
        int expectedApple = 50;
        int actualBanana = Storage.fruits.get(BANANA);
        int actualApple = Storage.fruits.get(APPLE);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    void apply_PurchaseOperation_Ok() {
        fruitOperationStrategy = new FruitOperationStrategyImpl(Map.of(FruitOperation.PURCHASE,
                new PurchaseOperationHandler(storageDao)));
        FruitTransaction fruitTransactionBanana = new FruitTransaction(FruitOperation.PURCHASE,
                BANANA, 25);
        FruitTransaction fruitTransactionApple = new FruitTransaction(FruitOperation.PURCHASE,
                APPLE, 10);
        fruitOperationStrategy.getHandler(FruitOperation.PURCHASE).apply(fruitTransactionApple);
        fruitOperationStrategy.getHandler(FruitOperation.PURCHASE).apply(fruitTransactionBanana);
        int expectedBanana = 25;
        int expectedApple = 40;
        int actualBanana = Storage.fruits.get(BANANA);
        int actualApple = Storage.fruits.get(APPLE);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    void apply_ReturnOperation_Ok() {
        fruitOperationStrategy = new FruitOperationStrategyImpl(Map.of(FruitOperation.RETURN,
                new ReturnOperationHandler(storageDao)));
        FruitTransaction fruitTransactionBanana = new FruitTransaction(FruitOperation.RETURN,
                BANANA, 20);
        FruitTransaction fruitTransactionApple = new FruitTransaction(FruitOperation.RETURN,
                APPLE, 10);
        fruitOperationStrategy.getHandler(FruitOperation.RETURN).apply(fruitTransactionApple);
        fruitOperationStrategy.getHandler(FruitOperation.RETURN).apply(fruitTransactionBanana);
        int expectedBanana = 70;
        int expectedApple = 60;
        int actualBanana = Storage.fruits.get(BANANA);
        int actualApple = Storage.fruits.get(APPLE);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    void apply_SupplyOperation_Ok() {
        fruitOperationStrategy = new FruitOperationStrategyImpl(Map.of(FruitOperation.SUPPLY,
                new SupplyOperationHandler(storageDao)));
        FruitTransaction fruitTransactionBanana = new FruitTransaction(FruitOperation.SUPPLY,
                BANANA, 5);
        FruitTransaction fruitTransactionApple = new FruitTransaction(FruitOperation.SUPPLY,
                APPLE, 32);
        fruitOperationStrategy.getHandler(FruitOperation.SUPPLY).apply(fruitTransactionApple);
        fruitOperationStrategy.getHandler(FruitOperation.SUPPLY).apply(fruitTransactionBanana);
        int expectedBanana = 55;
        int expectedApple = 82;
        int actualBanana = Storage.fruits.get(BANANA);
        int actualApple = Storage.fruits.get(APPLE);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    void apply_PurchaseOperationNegativeQuantity_notOk() {
        fruitOperationStrategy = new FruitOperationStrategyImpl(Map.of(FruitOperation.PURCHASE,
                new PurchaseOperationHandler(storageDao)));
        FruitTransaction fruitTransactionBanana = new FruitTransaction(FruitOperation.PURCHASE,
                BANANA, 100);
        FruitTransaction fruitTransactionApple = new FruitTransaction(FruitOperation.PURCHASE,
                APPLE, 200);
        assertThrows(RuntimeException.class, () ->
                fruitOperationStrategy.getHandler(FruitOperation.PURCHASE)
                        .apply(fruitTransactionApple));
        assertThrows(RuntimeException.class, () ->
                fruitOperationStrategy.getHandler(FruitOperation.PURCHASE)
                        .apply(fruitTransactionBanana));
    }
}
