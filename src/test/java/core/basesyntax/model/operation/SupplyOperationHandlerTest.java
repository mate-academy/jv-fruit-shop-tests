package core.basesyntax.model.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final Storage STORAGE = new Storage();
    private final OperationHandler operationHandler = new ReturnOperationHandler(STORAGE);

    @BeforeEach
    public void setUp() {
        STORAGE.getInventory().clear();
    }

    @Test
    public void handle_validData_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        operationHandler.handle(transaction);
        Assertions.assertTrue(STORAGE.getInventory().containsKey(transaction.getFruit()));
        Assertions.assertTrue(STORAGE.getInventory().containsValue(10));
    }

    @Test
    public void handle_validDataAlreadyInStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        STORAGE.getInventory().put("Melon", 100);
        operationHandler.handle(transaction);
        Assertions.assertTrue(STORAGE.getInventory().containsKey(transaction.getFruit()));
        Assertions.assertTrue(STORAGE.getInventory().containsValue(110));
    }

    @Test
    public void handle_zeroFruitSupplyQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(0);

        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Supply quantity cannot be zero");
    }

    @Test
    public void handle_negativeFruitSupplyQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(-5);

        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Supply quantity cannot be negative");
    }
}
