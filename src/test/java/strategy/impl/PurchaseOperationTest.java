package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dao.impl.FruitDaoImpl;
import db.FruitStorage;
import exception.FruitStorageException;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();
    private final PurchaseOperation purchaseOperation = new PurchaseOperation(fruitDao);

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void handle_validQuantity_ok() {
        FruitStorage.fruits.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        purchaseOperation.handle(transaction);

        int actual = FruitStorage.fruits.get("banana");
        assertEquals(15, actual);
    }

    @Test
    void handle_notEnoughQuantity_notOk() {
        FruitStorage.fruits.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 15);

        assertThrows(FruitStorageException.class, () ->
                purchaseOperation.handle(transaction));
    }
}
