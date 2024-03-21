package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final OperationHandler supply = new SupplyOperationHandler();
    private final FruitTransaction testTransaction = new FruitTransaction(
            Operation.SUPPLY, "banana", 100);
    private final FruitTransaction wrongTransaction = new FruitTransaction(
            Operation.SUPPLY, "banana", -183);

    @Test
    void supplyOperationHandler_WrongValue_ThrowsException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supply.apply(wrongTransaction));
        assertEquals("Wrong transaction value: -183 is less than zero", exception.getMessage());
    }

    @Test
    void supplyOperationHandler_OK_True() {
        Storage.dataBase.put("banana", 100);
        supply.apply(testTransaction);
        assertTrue(Storage.dataBase.containsKey("banana"));
        assertTrue(Storage.dataBase.containsValue(200));
    }

    @Test
    void supplyOperationHandler_IsApplicableOK_True() {
        assertTrue(supply.isAplicable(testTransaction));
    }
}
