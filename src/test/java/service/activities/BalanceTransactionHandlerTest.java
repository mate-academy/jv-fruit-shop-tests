package service.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.FruitShopStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionHandlerTest {
    private TransactionHandler transactionHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        transactionHandler = new BalanceTransactionHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void executeTransaction_Ok() {
        transactionHandler.executeTransaction(fruitTransaction);
        assertEquals(100, FruitShopStorage.fruitShop.get("banana"));
    }
}
