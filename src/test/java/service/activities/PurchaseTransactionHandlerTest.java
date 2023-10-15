package service.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.FruitShopStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionHandlerTest {
    private TransactionHandler transactionHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        transactionHandler = new PurchaseTransactionHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(99);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        FruitShopStorage.fruitShop.put("banana", 100);
    }

    @Test
    void executeTransaction_Ok() {
        String fruit = fruitTransaction.getFruit();
        int quantity = fruitTransaction.getQuantity();
        int quantityPurchase = FruitShopStorage.fruitShop.get(fruit);
        transactionHandler.executeTransaction(fruitTransaction);
        assertEquals(quantityPurchase - quantity, FruitShopStorage.fruitShop.get("banana"));
    }
}
