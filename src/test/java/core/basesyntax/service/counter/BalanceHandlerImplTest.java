package core.basesyntax.service.counter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.service.transaction.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerImplTest {

    private FruitTransaction fruitTransaction = new FruitTransaction();
    private OperationHandler operationHandler = new BalanceHandlerImpl();

    @AfterEach
    void tearDown() {
        Storage.getFruitTypesAndQuantity().clear();
    }

    @Test
    void handle_correctData_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.getFruitTypesAndQuantity().get(fruitTransaction.getFruit());
        assertEquals(actual, fruitTransaction.getQuantity());
    }

    @Test
    void handle_null_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
