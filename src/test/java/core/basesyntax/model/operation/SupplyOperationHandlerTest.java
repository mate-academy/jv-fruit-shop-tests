package core.basesyntax.model.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupplyOperationHandlerTest {
    private static final Storage storage = new Storage();
    private final OperationHandler operationHandler = new ReturnOperationHandler(storage);

    @BeforeEach
    public void setUp() {
        storage.getInventory().clear();
    }

    @Test
    public void handle_validData_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        operationHandler.handle(transaction);

        assertTrue(storage.getInventory().containsKey(transaction.getFruit()));
        assertTrue(storage.getInventory().containsValue(10));
    }

    @Test
    public void handle_validDataAlreadyInStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        storage.getInventory().put("Melon", 100);
        operationHandler.handle(transaction);

        assertTrue(storage.getInventory().containsKey(transaction.getFruit()));
        assertTrue(storage.getInventory().containsValue(110));
    }

    @Test
    public void handle_zeroFruitSupplyQuantity_notOk() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(0);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Supply quantity cannot be zero");
    }

    @Test
    public void handle_negativeFruitSupplyQuantity_notOk() {
        storage.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Melon");
        transaction.setQuantity(-5);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Supply quantity cannot be negative");
    }
}
