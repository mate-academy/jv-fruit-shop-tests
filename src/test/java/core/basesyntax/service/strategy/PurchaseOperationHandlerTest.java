package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static final String BEFORE_BALANCE_EXCEPTION_MESSAGE =
            "That is not possible to purchase a product"
                    + " that is not on the balance of the store.";
    private static final String MORE_THAN_BALANCE_EXCEPTION_MESSAGE =
            "There is not enough fruit "
                    + "to sell in the store, there are ";
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_validCase_isOk() {
        FruitStorage.STORAGE_MAP.put("banana", 20);
        purchaseOperationHandler.operate(VALID_INPUT);
        assertEquals(0, FruitStorage.STORAGE_MAP
                .get(VALID_INPUT.getFruit()));
    }

    @Test
    void operate_purchaseBeforeBalance_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            purchaseOperationHandler.operate(VALID_INPUT);
        });
        assertEquals(BEFORE_BALANCE_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void operate_purchaseMoreThanBalance_notOk() {
        FruitStorage.STORAGE_MAP.put("banana", 0);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            purchaseOperationHandler.operate(VALID_INPUT);
        });
        assertEquals(MORE_THAN_BALANCE_EXCEPTION_MESSAGE
                        + FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()),
                exception.getMessage());
    }

    @AfterAll
    static void afterAll() {
        FruitStorage.STORAGE_MAP.clear();
    }
}
