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

class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static final String VALID_EXCEPTION_MESSAGE = "That is not possible to return a product"
            + " that is not on the balance of the store.";
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @BeforeEach
    void setUp() {
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_validCase_isOk() {
        FruitStorage.STORAGE_MAP.put("banana", 20);
        returnOperationHandler.operate(VALID_INPUT);
        assertEquals(40, FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()));
    }

    @Test
    void operate_returnBeforeBalance_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            returnOperationHandler.operate(VALID_INPUT);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @AfterAll
    static void afterAll() {
        FruitStorage.STORAGE_MAP.clear();
    }
}
