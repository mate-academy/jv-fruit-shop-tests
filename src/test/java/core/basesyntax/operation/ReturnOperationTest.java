package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void testReturnOperation() {
        Storage.storage.put("banana", 50);
        OperationHandler handler = new ReturnOperation();

        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana",
                20
        ));
        assertEquals(70, Storage.storage.get("banana").intValue());
    }
}
