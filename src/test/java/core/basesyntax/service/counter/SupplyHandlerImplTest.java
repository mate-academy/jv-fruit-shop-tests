package core.basesyntax.service.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.service.transaction.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyHandlerImplTest {
    private static final int EXPECTED_AMOUNT = 85;
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private OperationHandler operationHandler = new SupplyHandlerImpl();

    @Test
    void handle_correctData_Ok() {
        Storage.getFruitTypesAndQuantity().put("banana", 60);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(25);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.getFruitTypesAndQuantity().get("banana");
        assertEquals(EXPECTED_AMOUNT, actual);
    }
}
