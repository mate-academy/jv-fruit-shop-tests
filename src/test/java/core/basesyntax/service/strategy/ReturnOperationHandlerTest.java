package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final OperationHandler RETURN_OPERATION_HANDLER = new ReturnOperationHandler();
    private static final String VALID_EXCEPTION_MESSAGE = "That is not possible to return a product"
            + " that is not on the balance of the store.";
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

    @Test
    void operate_validCase_isOk() {
        FruitStorage.STORAGE_MAP.put("banana", 20);
        RETURN_OPERATION_HANDLER.operate(VALID_INPUT);
        assertEquals(40, FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()));
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_returnBeforeBalance_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            RETURN_OPERATION_HANDLER.operate(VALID_INPUT);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
        FruitStorage.STORAGE_MAP.clear();
    }
}
