package service.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.FruitShopStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionHandlerTest {
    public static final int QUANTITY_ONE = 1;
    public static final int QUANTITY_TWO = 2;
    public static final int QUANTITY_TREE = 3;
    private TransactionHandler transactionHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        transactionHandler = new PurchaseTransactionHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(QUANTITY_ONE);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        FruitShopStorage.fruitShop.put("banana", QUANTITY_TWO);
    }

    @Test
    void executeTransaction_Ok() {
        String fruit = fruitTransaction.getFruit();
        int quantity = fruitTransaction.getQuantity();
        int quantityPurchase = FruitShopStorage.fruitShop.get(fruit);
        transactionHandler.executeTransaction(fruitTransaction);
        assertEquals(quantityPurchase - quantity, FruitShopStorage.fruitShop.get("banana"));
    }

    @Test
    void executeTransaction_NotOk() {
        fruitTransaction.setQuantity(QUANTITY_TREE);
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.executeTransaction(fruitTransaction));
    }
}
