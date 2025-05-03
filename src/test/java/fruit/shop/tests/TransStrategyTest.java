package fruit.shop.tests;

import fruit.shop.db.Storage;
import fruit.shop.model.Fruit;
import fruit.shop.model.FruitTransaction;
import fruit.shop.model.TransactionType;
import fruit.shop.service.TransactionStrategyImpl;
import fruit.shop.service.transaction.BalanceTransactionHandler;
import fruit.shop.service.transaction.PurchaseTransactionHandler;
import fruit.shop.service.transaction.ReturnTransactionHandler;
import fruit.shop.service.transaction.SupplyTransactionHandler;
import fruit.shop.service.transaction.TransactionHandler;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransStrategyTest {
    private static final Map<TransactionType, TransactionHandler> TRANSACTION_HANDLER_MAP
            = Map.ofEntries(
            Map.entry(TransactionType.BALANCE, new BalanceTransactionHandler()),
            Map.entry(TransactionType.SUPPLY, new SupplyTransactionHandler()),
            Map.entry(TransactionType.PURCHASE, new PurchaseTransactionHandler()),
            Map.entry(TransactionType.RETURN, new ReturnTransactionHandler())
    );
    private static final TransactionStrategyImpl TRANSACTION_STRATEGY
            = new TransactionStrategyImpl(TRANSACTION_HANDLER_MAP);

    @AfterEach
    public void afterEachTest() {
        Storage.clear();
    }

    @Test
    public void strategy_balance_ok() {
        int expected = 10;
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, expected);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        int actual = Storage.get(Fruit.APPLE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void strategy_supply_ok() {
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, 10);
        FruitTransaction supply = new FruitTransaction(Fruit.APPLE,
                TransactionType.SUPPLY, 10);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        TRANSACTION_STRATEGY.executeTransaction(supply);
        int expected = 20;
        int actual = Storage.get(Fruit.APPLE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void strategy_return_ok() {
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, 10);
        FruitTransaction returnTrans = new FruitTransaction(Fruit.APPLE,
                TransactionType.RETURN,
                10);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        TRANSACTION_STRATEGY.executeTransaction(returnTrans);
        int expected = 20;
        int actual = Storage.get(Fruit.APPLE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void strategy_purchase_ok() {
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, 10);
        FruitTransaction purchase = new FruitTransaction(Fruit.APPLE,
                TransactionType.PURCHASE, 10);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        TRANSACTION_STRATEGY.executeTransaction(purchase);
        int expected = 0;
        int actual = Storage.get(Fruit.APPLE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void strategy_purchase_notEnoughStock_notOk() {
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, 5);
        FruitTransaction purchase = new FruitTransaction(Fruit.APPLE,
                TransactionType.PURCHASE, 10);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        Assert.assertThrows(fruit.shop.service.transaction.QuantityValidationException.class,
                () -> TRANSACTION_STRATEGY.executeTransaction(purchase));
    }

    @Test
    public void strategy_purchase_exactStock_ok() {
        FruitTransaction balance = new FruitTransaction(Fruit.APPLE,
                TransactionType.BALANCE, 10);
        FruitTransaction purchase = new FruitTransaction(Fruit.APPLE,
                TransactionType.PURCHASE, 10);
        TRANSACTION_STRATEGY.executeTransaction(balance);
        TRANSACTION_STRATEGY.executeTransaction(purchase);
        int expected = 0;
        int actual = Storage.get(Fruit.APPLE);
        Assertions.assertEquals(expected, actual);
    }
}
