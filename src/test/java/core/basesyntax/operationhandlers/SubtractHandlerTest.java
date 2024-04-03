package core.basesyntax.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SubtractHandlerTest {
    private static final OperationHandler SUBTRACT_HANDLER = new SubtractHandler();
    private static final FruitTransaction CORRECT_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
    private static final FruitTransaction NEGATIVE_QUANTITY_FRUIT_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",-20);

    @Test
    void handle_correctData_ok() {
        FruitStorage.FRUIT_STORAGE.put("banana",100);
        SUBTRACT_HANDLER.handle(CORRECT_FRUIT_TRANSACTION);
        int actual = FruitStorage.FRUIT_STORAGE.get("banana");
        int expected = 80;
        assertEquals(expected,actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitStorage.FRUIT_STORAGE.put("banana",100);
        assertThrows(RuntimeException.class,
                () -> SUBTRACT_HANDLER.handle(NEGATIVE_QUANTITY_FRUIT_TRANSACTION));
    }

    @Test
    void handle_subtractMoreThanExistInStorage_notOk() {
        FruitStorage.FRUIT_STORAGE.put("banana",10);
        assertThrows(RuntimeException.class,
                () -> SUBTRACT_HANDLER.handle(CORRECT_FRUIT_TRANSACTION));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.FRUIT_STORAGE.clear();
    }
}
