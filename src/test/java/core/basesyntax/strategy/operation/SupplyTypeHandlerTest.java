package core.basesyntax.strategy.operation;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTypeHandlerTest {
    private static final OperationHandlers OPERATION_HANDLERS
            = new SupplyTypeHandler();
    private static final FruitTransaction.Operation VALID_OPERATION =
            FruitTransaction.Operation.SUPPLY;
    private static final String VALID_NAME = "banana";
    private static final Integer VALID_QUANTITY = 100;
    private static final Integer EXPECTED_QUANTITY = 200;
    private static final FruitTransaction VALID_FRUIT_TRANSACTION
            = new FruitTransaction(VALID_OPERATION, VALID_NAME, VALID_QUANTITY);

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void supplyHandleTransaction_ValidData_Ok() {
        storage.put(VALID_NAME, VALID_QUANTITY);
        OPERATION_HANDLERS.handleTransaction(VALID_FRUIT_TRANSACTION);
        assertEquals(Map.of(VALID_NAME,EXPECTED_QUANTITY).entrySet(),storage.entrySet());
    }
}
