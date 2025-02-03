package core.basesyntax.model.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PurchaseOperationHandlerTest {
    private static final Storage STORAGE = new Storage();
    private final OperationHandler operationHandler = new PurchaseOperationHandler(STORAGE);

    @BeforeEach
    public void setUp() {
        STORAGE.getInventory().clear();
    }

    @Test
    public void handle_validPresentInStorageTransaction_Ok() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        operationHandler.handle(transaction);

        assertTrue(STORAGE.getInventory().containsKey("Melon"));
        assertTrue(STORAGE.getInventory().containsValue(15));
    }

    @Test
    public void handle_zeroFruitPurchaseQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(0);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Purchase quantity cannot be zero");
    }

    @Test
    public void handle_negativeFruitPurchaseQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Melon");
        transaction.setQuantity(-5);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Purchase quantity cannot be negative");
    }

    @Test
    public void handle_notEnoughFruitsInStorage_notOk() {
        STORAGE.getInventory().put("Melon", 25);
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
