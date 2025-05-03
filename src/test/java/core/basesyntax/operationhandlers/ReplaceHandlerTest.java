package core.basesyntax.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReplaceHandlerTest {
    private static final OperationHandler REPLACE_HANDLER = new ReplaceHandler();
    private static final FruitTransaction CORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
    private static final FruitTransaction NEGATIVE_QUANTITY_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",-20);

    @Test
    void handle_correctData_ok() {
        FruitStorage.FRUIT_STORAGE.put("banana",100);
        REPLACE_HANDLER.handle(CORRECT_FRUIT_TRANSACTION);
        int actual = FruitStorage.FRUIT_STORAGE.get("banana");
        int expected = 120;
        assertEquals(expected,actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> REPLACE_HANDLER.handle(NEGATIVE_QUANTITY_FRUIT_TRANSACTION));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.FRUIT_STORAGE.clear();
    }
}
