package core.basesyntax.service.operations;

import static core.basesyntax.storage.Storage.shop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        purchaseOperation = new PurchaseOperation();
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    public void addFruitsToBalance() {
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        FruitTransaction bananaBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction cherryBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "cherry", 100);
        balanceOperation.processTransaction(appleBalance);
        balanceOperation.processTransaction(bananaBalance);
        balanceOperation.processTransaction(cherryBalance);
    }

    @AfterEach
    public void makeShopEmpty() {
        shop.clear();
    }

    @Test
    public void purchaseAllFruits_Ok() {
        FruitTransaction applePurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1);
        FruitTransaction bananaPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        FruitTransaction cherryPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 100);
        purchaseOperation.processTransaction(applePurchase);
        purchaseOperation.processTransaction(bananaPurchase);
        purchaseOperation.processTransaction(cherryPurchase);
        assertEquals(0, shop.get("apple"));
        assertEquals(0, shop.get("banana"));
        assertEquals(0, shop.get("cherry"));
    }

    @Test
    public void toHighPurchaseFruits_NotOk() {
        FruitTransaction applePurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1);
        FruitTransaction bananaPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        FruitTransaction cherryPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 101);
        purchaseOperation.processTransaction(applePurchase);
        purchaseOperation.processTransaction(bananaPurchase);
        Exception exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.processTransaction(cherryPurchase));
        String expectedMessage = "Balance is too low for this purchase";
        String actualMessage = exception.getMessage();
        assertEquals(0, shop.get("apple"));
        assertEquals(0, shop.get("banana"));
        assertEquals(expectedMessage, actualMessage);
        assertEquals(100, shop.get("cherry"));
    }

    @Test
    public void purchaseFruitWhichIsNotInShop_NotOk() {
        FruitTransaction applePurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1);
        FruitTransaction bananaPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        FruitTransaction cherryPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 100);
        FruitTransaction pearPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pear", 20);
        purchaseOperation.processTransaction(applePurchase);
        purchaseOperation.processTransaction(bananaPurchase);
        purchaseOperation.processTransaction(cherryPurchase);
        Exception exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.processTransaction(pearPurchase));
        String expectedMessage = "There is no such fruit in shop";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        assertEquals(0, shop.get("apple"));
        assertEquals(0, shop.get("banana"));
        assertEquals(0, shop.get("cherry"));
        assertEquals(null, shop.get("pear"));
    }
}
