package service.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.FruitShopStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTransactionHandlerTest {
    private TransactionHandler transactionHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        transactionHandler = new SupplyTransactionHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(99);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        FruitShopStorage.fruitShop.put("banana", 100);
    }

    @Test
    void executeTransaction_Ok() {
        String fruit = fruitTransaction.getFruit();
        int quantity = fruitTransaction.getQuantity();
        int quantitySupply = FruitShopStorage.fruitShop.get(fruit);
        transactionHandler.executeTransaction(fruitTransaction);
        assertEquals(quantity + quantitySupply, FruitShopStorage.fruitShop.get("banana"));
    }
}
