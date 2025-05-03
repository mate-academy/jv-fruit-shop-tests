package core.basesyntax.model.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final Storage storage = new Storage();
    private final OperationHandler operationHandler = new PurchaseOperationHandler(storage);

    @AfterEach
    public void tearDown() {
        storage.getInventory().clear();
    }

    @Test
    public void handle_validPresentInStorageTransaction_Ok() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        operationHandler.handle(transaction);

        assertTrue(storage.getInventory().containsKey("Melon"));
        assertTrue(storage.getInventory().containsValue(15));
    }

    @Test
    public void handle_zeroFruitPurchaseQuantity_notOk() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(0);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Purchase quantity cannot be zero");
    }

    @Test
    public void handle_negativeFruitPurchaseQuantity_notOk() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(-5);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Purchase quantity cannot be negative");
    }

    @Test
    public void handle_notEnoughFruitsInStorage_notOk() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(30);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Not enough fruits in storage");
    }

    @Test
    public void handle_fruitIsNotPresentInStorage_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(30);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Fruit is not present in storage");
    }
}
