package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final OperationHandler PURCHASE_OPERATION_HANDLER =
            new PurchaseOperationHandler();
    private static final String BEFORE_BALANCE_EXCEPTION_MESSAGE =
            "That is not possible to purchase a product"
                    + " that is not on the balance of the store.";
    private static final String MORE_THAN_BALANCE_EXCEPTION_MESSAGE =
            "There is not enough fruit "
                    + "to sell in the store, there are ";
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);

    @Test
    void operate_validCase_isOk() {
        FruitStorage.STORAGE_MAP.put("banana", 20);
        PURCHASE_OPERATION_HANDLER.operate(VALID_INPUT);
        assertEquals(0, FruitStorage.STORAGE_MAP
                .get(VALID_INPUT.getFruit()));
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_purchaseBeforeBalance_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            PURCHASE_OPERATION_HANDLER.operate(VALID_INPUT);
        });
        assertEquals(BEFORE_BALANCE_EXCEPTION_MESSAGE, exception.getMessage());
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_purchaseMoreThanBalance_notOk() {
        FruitStorage.STORAGE_MAP.put("banana", 0);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            PURCHASE_OPERATION_HANDLER.operate(VALID_INPUT);
        });
        assertEquals(MORE_THAN_BALANCE_EXCEPTION_MESSAGE
                        + FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()),
                exception.getMessage());
        FruitStorage.STORAGE_MAP.clear();
    }
}
