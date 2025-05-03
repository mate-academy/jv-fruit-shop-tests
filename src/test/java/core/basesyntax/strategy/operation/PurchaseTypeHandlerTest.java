package core.basesyntax.strategy.operation;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTypeHandlerTest {

    private static final OperationHandlers OPERATION_HANDLERS
            = new PurchaseTypeHandler();
    private static final FruitTransaction.Operation VALID_OPERATION =
            FruitTransaction.Operation.PURCHASE;
    private static final String VALID_NAME = "banana";
    private static final Integer VALID_QUANTITY = 100;
    private static final Integer NOT_VALID_QUANTITY = 200;
    private static final Integer EXPECTED_QUANTITY = 0;

    private static final FruitTransaction VALID_FRUIT_TRANSACTION
            = new FruitTransaction(VALID_OPERATION, VALID_NAME, VALID_QUANTITY);
    private static final FruitTransaction NOT_VALID_FRUIT_TRANSACTION
            = new FruitTransaction(VALID_OPERATION, VALID_NAME, NOT_VALID_QUANTITY);

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void purchaseHandleTransaction_ValidData_Ok() {
        storage.put(VALID_NAME, VALID_QUANTITY);
        OPERATION_HANDLERS.handleTransaction(VALID_FRUIT_TRANSACTION);
        assertEquals(Map.of(VALID_NAME, EXPECTED_QUANTITY).entrySet(), storage.entrySet());
    }

    @Test
    void handleTransaction_NotValidData_notOk() {
        assertThrows(RuntimeException.class, () -> OPERATION_HANDLERS
                .handleTransaction(NOT_VALID_FRUIT_TRANSACTION));
    }
}
