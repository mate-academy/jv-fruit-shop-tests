package core.basesyntax.strategy.operation;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTypeHandlerTest {
    private static final String VALID_NAME = "banana";
    private static final Integer VALID_QUANTITY = 100;
    private static final FruitTransaction VALID_FRUIT_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.BALANCE,
            VALID_NAME,VALID_QUANTITY);
    private static final OperationHandlers OPERATION_HANDLERS
            = new BalanceTypeHandler();

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void balanceHandleTransaction_ValidData_Ok() {
        OPERATION_HANDLERS.handleTransaction(VALID_FRUIT_TRANSACTION);
        assertEquals(Map.of(VALID_NAME,VALID_QUANTITY).entrySet(),storage.entrySet());

    }
}
