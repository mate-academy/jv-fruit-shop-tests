package core.basesyntax.service.counter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.service.transaction.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseHandlerImplTest {
    private static final int EXPECTED_AMOUNT = 30;
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private OperationHandler operationHandler = new PurchaseHandlerImpl();

    @Test
    void handle_correctData_Ok() {
        Storage.getFruitTypesAndQuantity().put("banana", 50);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.getFruitTypesAndQuantity().get("banana");
        assertEquals(EXPECTED_AMOUNT, actual);
    }

    @Test
    void handle_null_NotOk() {
        Storage.getFruitTypesAndQuantity().put("banana", 50);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
