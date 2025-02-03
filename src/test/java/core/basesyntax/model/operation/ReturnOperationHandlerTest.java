package core.basesyntax.model.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReturnOperationHandlerTest {
    private static final Storage STORAGE = new Storage();
    private final OperationHandler operationHandler = new ReturnOperationHandler(STORAGE);

    @BeforeEach
    public void setUp() {
        STORAGE.getInventory().clear();
    }

    @Test
    public void handle_validData_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        operationHandler.handle(transaction);
        assertTrue(STORAGE.getInventory().containsKey(transaction.getFruit()));
        assertTrue(STORAGE.getInventory().containsValue(10));
    }

    @Test
    public void handle_validDataAlreadyInStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Melon");
        transaction.setQuantity(10);
        STORAGE.getInventory().put("Melon", 100);
        operationHandler.handle(transaction);

        assertTrue(STORAGE.getInventory().containsKey(transaction.getFruit()));
        assertTrue(STORAGE.getInventory().containsValue(110));
    }

    @Test
    public void handle_zeroFruitReturnQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Melon");
        transaction.setQuantity(0);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Return quantity cannot be zero");
    }

    @Test
    public void handle_negativeFruitReturnQuantity_notOk() {
        STORAGE.getInventory().put("Melon", 25);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Melon");
        transaction.setQuantity(-5);

        assertThrows(RuntimeException.class,
                () -> operationHandler.handle(transaction), "Return quantity cannot be negative");
    }
}
